package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.service.impl.PersonServiceImpl;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/person")
    public class PersonController {


    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private DozerBeanMapper mapper;

    @GetMapping
    public Collection<PersonDTO> getAll() {
        return personService.findAll();
    }

    @PostMapping
    public void saveNewPerson(@RequestBody Person person) {
        personService.create(person);
    }

    @PostMapping("/{id}/{rate}")
    public void updateRating(@PathVariable int id, @PathVariable int rate) {
        personService.updateRating(id,rate);
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable int id){
        return mapper.map(personService.getById(id), PersonDTO.class);
    }


}
