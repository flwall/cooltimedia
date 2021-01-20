package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
