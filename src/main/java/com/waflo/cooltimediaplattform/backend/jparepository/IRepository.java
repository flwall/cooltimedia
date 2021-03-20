package com.waflo.cooltimediaplattform.backend.jparepository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IRepository<T, IDType> {
    List<T> findAll();
    Optional<T> findById(IDType id);
    T save(T instance);
    void delete(T obj);

}
