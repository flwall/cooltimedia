package com.waflo.cooltimediaplattform.backend.jparepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Transactional
public class JpaRepository<T> implements IRepository<T,Long>{
    private EntityManager em;//= DataAccessor.getInstance().factory.createEntityManager();
    private final Class<T> persistentClass;


    public JpaRepository(EntityManager em, Class<T> type){
        this.em=em;
        this.persistentClass = type;

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
    @Transactional
    public T save(T instance) {
        em.persist(instance);
        return instance;
    }

    @Override
    @Transactional
    public void delete(T obj) {
        em.remove(em.contains(obj)?em:em.merge(obj));

    }
}
