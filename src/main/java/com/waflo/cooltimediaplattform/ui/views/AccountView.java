package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.model.File;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.model.User;
import com.waflo.cooltimediaplattform.backend.repository.FileContentStore;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.FileService;
import com.waflo.cooltimediaplattform.backend.service.UserService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.data.CategoryDataProvider;
import com.waflo.cooltimediaplattform.ui.data.PersonDataProvider;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Route(value = "account", layout = MainLayout.class)
@Secured("ROLE_USER")
@PageTitle("Mein Konto | CTM")
public class AccountView extends VerticalLayout {

    private final User user;
    private final FileService fileService;
    private final FileContentStore store;
    private final UserService userService;
    private final PersonDataProvider personDataProvider;
    private final CategoryDataProvider categoryDataProvider;

    private final Notification notification = new Notification("Benutzername erfolgreich geändert", 3000);


    public AccountView(UserSession userSession, FileService fileService, FileContentStore store, UserService userService, PersonDataProvider personDataProvider, CategoryDataProvider categoryDataProvider) {
        this.user = userSession.getUser();
        this.fileService = fileService;


        this.store = store;
        this.userService = userService;
        this.personDataProvider = personDataProvider;
        this.categoryDataProvider = categoryDataProvider;

        initMenuBar();
        initAccount(null);

    }

    private void initPersons(Object o) {
        deleteContent();

        var head = new H1("Deine Authoren und Akteure");
        var exp = new Text("Hier kannst du persönliche Authoren definieren, welche du später als Authoren/Schauspieler bei deinen Medien festlegen kannst.");

        Crud<Person> crud = new Crud<>(Person.class, createPersonEditor());


        crud.setDataProvider(personDataProvider);
        crud.addSaveListener(e -> personDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> personDataProvider.delete(e.getItem()));

        crud.getGrid().removeColumnByKey("image");

        crud.getGrid().addComponentColumn(person -> {
            var img = person.getImage();
            if (img == null) return new Div();

            var comp = new Image("/files/" + img.getId(), "Pic");
            comp.setWidth("48px");
            comp.setHeight("48px");
            return comp;
        }).setHeader("Bild").setKey("image");


        crud.getGrid().removeColumnByKey("id");

        var cols = crud.getGrid().getColumns();
        crud.getGrid().setColumnOrder(cols.stream().filter(s -> s.getKey().equals("name")).findFirst().get(),
                cols.stream().filter(s -> s.getKey().equals("birthDate")).findFirst().get(),
                cols.stream().filter(s -> s.getKey().equals("image")).findFirst().get(),
                cols.stream().filter(s -> s.getKey().equals("vaadin-crud-edit-column")).findFirst().get());

        crud.addThemeVariants(CrudVariant.NO_BORDER);
        contentDiv.add(head, exp, crud);

        add(contentDiv);

    }

    private CrudEditor<Person> createPersonEditor() {
        TextField name = new TextField("Name");
        DatePicker birth = new DatePicker("Geburtsdatum");
        var rec = new FileBuffer();
        Upload pic = new Upload(rec);


        FormLayout form = new FormLayout(name, birth);
        form.addFormItem(pic, new Label("Bild"));

        Binder<Person> binder = new Binder<>(Person.class);
        binder.bind(name, Person::getName, Person::setName);
        binder.bind(birth, Person::getBirthDate, Person::setBirthDate);

        if (binder.getBean() == null)
            binder.setBean(new Person());
        pic.addSucceededListener(l -> {
            var f = new File();
            f.setCreated(LocalDate.now());
            f.setName(l.getFileName());
            f.setMimeType(l.getMIMEType());
            File toRemove = null;
            if (binder.getBean().getImage() != null) {
                toRemove = binder.getBean().getImage();
            }
            store.setContent(f, rec.getInputStream());
            fileService.save(f);
            binder.getBean().setImage(f);

            if (toRemove != null) {
                fileService.delete(toRemove);
                store.unsetContent(toRemove);

            }
        });


        return new BinderCrudEditor<>(binder, form);
    }

    private void deleteContent() {

        if (contentDiv != null) {
            remove(contentDiv);
        }
        contentDiv = new Div();
    }

    private Div contentDiv;

    private void initMenuBar() {
        var menu = new MenuBar();
        menu.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        menu.addItem("Mein Konto", this::initAccount);
        menu.addItem("Meine Authoren", this::initPersons);
        menu.addItem("Meine Kategorien", this::initCategories);

        menu.addItem(new Anchor("/logout", "Ausloggen"));        //change color

        add(menu);
        add(notification);
    }

    private void initCategories(ClickEvent<MenuItem> menuItemClickEvent) {
        deleteContent();


        var head = new H1("Deine Kategorien");
        var exp = new Text("Hier kannst du persönliche Kategorien definieren, welche du später zur Kategorisierung deiner Medien verwenden kannst.");

        Crud<Category> crud = new Crud<>(Category.class, createCategoryEditor());


        crud.setDataProvider(categoryDataProvider);
        crud.addSaveListener(e -> categoryDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> categoryDataProvider.delete(e.getItem()));


        crud.getGrid().removeColumnByKey("id");
        crud.getGrid().removeColumnByKey("onDemands");
        crud.addThemeVariants(CrudVariant.NO_BORDER);
        contentDiv.add(head, exp, crud);

        add(contentDiv);

    }

    private CrudEditor<Category> createCategoryEditor() {
        TextField name = new TextField("Name");
        ComboBox<Category> parent = new ComboBox<>("übergeornete Kategorie: ");
        parent.setItemLabelGenerator(Category::getName);
        parent.setItems(categoryDataProvider.fetch(new Query<>()));


        FormLayout form = new FormLayout(name, parent);


        Binder<Category> binder = new Binder<>(Category.class);
        binder.bind(name, Category::getName, Category::setName);
        binder.bind(parent, Category::getParentCategory, Category::setParentCategory);

        return new BinderCrudEditor<>(binder, form);
    }

    private void initAccount(ClickEvent<MenuItem> menuItemClickEvent) {
        deleteContent();


        var username = new TextField("Benutzername", user.getUsername(), "Benutzername");
        contentDiv.add(username);
        final Image img;
        if (user.getProfile_pic() != null) {
            img = new Image("/files/" + user.getProfile_pic().getId(), "Profilbild");

        } else
            img = new Image();
        img.setWidth("96px");
        img.setHeight("96px");
        contentDiv.add(img);
        var picLabel = new Label("Profilbild ändern");
        var rec = new FileBuffer();
        var profilePic = new Upload(rec);
        profilePic.setAcceptedFileTypes("image/*");
        profilePic.addSucceededListener(l -> {
            var f = new File();
            f.setMimeType(l.getMIMEType());
            f.setCreated(LocalDate.now());
            f.setName(l.getFileName());

            File picToUnset = null;
            if (user.getProfile_pic() != null) {
                picToUnset = user.getProfile_pic();
            }
            store.setContent(f, rec.getInputStream());
            user.setProfile_pic(f);
            fileService.save(f);
            userService.save(user);


            img.setSrc("/files/" + user.getProfile_pic().getId());

            if (picToUnset != null) {
                store.unsetContent(picToUnset);
                fileService.delete(picToUnset);
            }
        });

        Button saveBtn = new Button("Speichern");
        saveBtn.addClickListener(l -> {
            user.setUsername(username.getValue());
            userService.save(user);
            notification.open();

        });
        var form = new FormLayout(picLabel, profilePic, saveBtn);
        contentDiv.add(form);

        add(contentDiv);
    }


}

class PersonComparator implements Comparator<Grid.Column<Person>> {

    static Map<String, Integer> ordering = new HashMap<>();

    static {
        ordering.put("name", 0);
        ordering.put("birthDate", 1);
        ordering.put("image", 2);
        ordering.put("vaadin-crud-edit-column", 3);

    }

    @Override
    public int compare(Grid.Column<Person> o1, Grid.Column<Person> o2) {
        return ordering.get(o1.getKey()).compareTo(ordering.get(o2.getKey()));
    }
}