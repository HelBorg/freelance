package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    TaskRepository repository;

    @Autowired
    PersonService personService;


    public Tasks createNew(Person person) {
        Tasks add = new Tasks();
        add.setName("New task");
        add.setStatus("IN_DESIGN");
        add.setTaskAuthor(person);
        return repository.save(add);
    }


}
