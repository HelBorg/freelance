package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping
    public ResponseEntity<Pager<PersonDTO>> getAll(@RequestParam("size") Optional<Integer> pageSize,
                                                   @RequestParam("page") Optional<Integer> pageNumber,
                                                   @RequestParam("findName") Optional<String> findName) {
        PageAndSort pageAndSort = new PageAndSort
                (Sort.by("rating").descending(), pageNumber.orElse(0),
                        pageSize.orElse(10), findName.orElse(""));

        Pager<PersonDTO> pager = personService.findAll(pageAndSort);

        logger.info("Request to get users: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable int id) {
        logger.info("Request to get user by id: id - {}", id);
        return personService.getById(id);
    }

    @PostMapping
    public void saveNewPerson(@RequestBody Person person) {
        logger.info("Request to save new user: {}", person.getName());
        personService.create(person);
    }

    @PostMapping("/{id}/{rate}")
    public void updateRating(@PathVariable int id, @PathVariable int rate) {
        logger.info("Request to update user rating: rating - {}, person id - {}",rate, id);
        personService.updateRating(id, rate);
    }



}
