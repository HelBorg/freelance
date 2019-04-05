package com.freelance.project.demo.controller;


import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.freelance.project.demo.models.Person.UserRole.USER;

@Controller
@RequestMapping("/api/v1")
public class RegistrationController {


    @Autowired
     private PersonRepository personRepository;


    @PostMapping("/registration")
    public String addUser(Person person){

    Person dbPerson = personRepository.findPersonByEmail(person.getEmail());

    if (dbPerson != null){
        return "/resources/hello";
    }

    person.setRole(USER);
    personRepository.save(person);

        return "redirect:/resources/hello";
    }
}
