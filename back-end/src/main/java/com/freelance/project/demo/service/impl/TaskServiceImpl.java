package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.models.Tasks;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    TaskRepository repository;


    public void createNew(Tasks task) {
        Tasks add = new Tasks();
        add.setName(task.getName());
        add.setStatus(task.getStatus());
        add.setTaskAuthor(task.getTaskAuthor());
        repository.save(add);

    }

}
