package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/person")
    public class PersonController {


    @Autowired
    private PersonService service;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public ResponseEntity<Pager<PersonDTO>> getAll(@RequestParam("size") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> pageNumber,
                                        @RequestParam("sort") Optional<String> sort) {
        Pager<PersonDTO> pager = service.findAll(pageSize, pageNumber, sort);
        logger.info("Request to get users: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

    @PostMapping
    public void saveNewPerson(@RequestBody Person person) {
        logger.info("Request to save new user: {}", person.getName());
        service.create(person);
    }


}
