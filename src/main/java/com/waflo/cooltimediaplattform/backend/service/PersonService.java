package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repo) {
        this.repository = repo;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Optional<Person> findById(long id) {
        return repository.findById(id);
    }


    public void save(Person item) {
        repository.save(item);
    }

    public void delete(Person item) {
        repository.delete(item);
    }
}
