package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;

import java.util.Optional;

public interface PersonService {


    Pager<PersonDTO> findAll(PageAndSort pageAndSort);

    Person findByEmail(String email);

    void create(Person person);

    PersonDTO getById(int id);

    void updateRating(int id, int rate);

}
