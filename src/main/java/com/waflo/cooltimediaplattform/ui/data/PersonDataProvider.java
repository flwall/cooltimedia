package com.waflo.cooltimediaplattform.ui.data;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.PersonService;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SpringComponent
public class PersonDataProvider extends AbstractDataProvider<Person, CrudFilter> {

    private final PersonService personService;
    private final UserSession session;

    public PersonDataProvider(PersonService personService, UserSession session) {

        this.personService = personService;
        this.session = session;
    }


    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public int size(Query<Person, CrudFilter> query) {
        return (int) fetch(query).count();
    }

    @Override
    public Stream<Person> fetch(Query<Person, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Person> stream = personService.findAllByUser(session.getUser().getId()).stream();       //find only for appropriate user

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }
    private static Predicate<Person> predicate(CrudFilter filter) {
        // For RDBMS just generate a WHERE clause
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Person>) person -> {
                    try {
                        Object value = valueOf(constraint.getKey(), person);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }
    private static Object valueOf(String fieldName, Person person) {
        try {
            Field field = Person.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(person);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Comparator<Person> comparator(CrudFilter filter) {
        // For RDBMS just generate an ORDER BY clause
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Person> comparator
                                = Comparator.comparing(person ->
                                (Comparable) valueOf(sortClause.getKey(), person));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<Person>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    public void persist(Person item) {
        personService.save(item);
    }

    public void delete(Person item) {
        personService.delete(item);
    }
}
