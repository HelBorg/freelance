package com.freelance.project.demo.service;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;

public interface TaskService {

    Tasks createNew(Person person);
}
