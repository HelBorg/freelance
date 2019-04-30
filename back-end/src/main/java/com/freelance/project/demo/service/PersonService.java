package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;

import java.util.List;

public interface PersonService {


    List<PersonDTO> findAll();

    Person findByEmail(String email);

    void create(Person person);

    Person getById(int id);

}
