package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.service.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/person")
    public class PersonController {

    @Autowired
    private PersonServiceImpl service;

    @GetMapping
    public Collection<PersonDTO> persons() {
        return service.findAll();
    }
    
}
