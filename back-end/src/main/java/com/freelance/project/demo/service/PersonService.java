package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;

import java.util.Optional;

public interface PersonService {


    Pager<PersonDTO> findAll(Optional<Integer> pageSize,
                           Optional<Integer> pageNumber,
                           Optional<String> pageSort);

    Person findByEmail(String email);

    void create(Person person);

}
