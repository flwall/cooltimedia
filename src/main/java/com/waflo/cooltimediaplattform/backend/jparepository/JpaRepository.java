package com.waflo.cooltimediaplattform.backend.jparepository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.xml.crypto.Data;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


public class JpaRepository<T> implements IRepository<T,Long>{
    private static final EntityManager em= DataAccessor.getInstance().factory.createEntityManager();
    private final Class<T> persistentClass;

    public JpaRepository(){
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }


    @Override
    public List<T> findAll() {
        var criteriaQuery=em.getCriteriaBuilder().createQuery(persistentClass);
        criteriaQuery.select(criteriaQuery.from(persistentClass));

        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.of(em.find(persistentClass, id));
    }

    @Override
    public T save(T instance) {
        em.persist(instance);
        return instance;
    }

    @Override
    public void delete(T obj) {
        em.remove(obj);


    }
}
