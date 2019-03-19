package com.freelance.project.demo.Controllers;

import com.freelance.project.demo.dao.IPersonDao;
import com.freelance.project.demo.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Test controller for dao

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private IPersonDao iPersonDao;


    @GetMapping("/all")
    public List<Person> getAllPersons(){
        return iPersonDao.findAll();

   }
}

