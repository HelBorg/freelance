package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByPersonId(int id);
    Person findByEmail(String email);

}
