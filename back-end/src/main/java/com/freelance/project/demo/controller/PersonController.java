package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/persons")
    public class PersonController {


    @Autowired
    private PersonServiceImpl service;


    @GetMapping
    public Collection<PersonDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public void saveNewPerson(@RequestBody Person person) {
        service.create(person);
    }


}
