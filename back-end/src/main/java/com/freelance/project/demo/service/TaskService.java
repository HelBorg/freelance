package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;

public interface TaskService {

    Tasks createNew(Person person);

    TaskDTO loadTask(int id);

    void updateTask(TaskDTO task);
}
