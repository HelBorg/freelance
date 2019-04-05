package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Skills;
import com.freelance.project.demo.service.impl.PersonServiceImpl;
import com.freelance.project.demo.service.impl.SkillsServiceImpl;
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

    @Autowired
    private SkillsServiceImpl skillsService;

    @GetMapping
    public Collection<PersonDTO> getAllPersons() {
        return service.findAll();
    }


    @GetMapping("/skills")
    public Collection<Skills> getAllSkills(){
        return skillsService.findAllSkills();
    }

   /* @GetMapping()
    public Person getByEmail(String email) {
        return service.findByEmail(email);
    }*/
}
