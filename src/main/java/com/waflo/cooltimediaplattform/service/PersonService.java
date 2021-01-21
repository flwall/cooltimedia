package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Person;
import com.waflo.cooltimediaplattform.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository repository;

    public PersonService(PersonRepository repo) {
        this.repository = repo;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Optional<Person> findById(long id) {
        return repository.findById(id);
    }


}
