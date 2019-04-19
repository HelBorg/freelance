package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    TaskRepository repository;

    @Autowired
    private DozerBeanMapper mapper;

    @Transactional
    public Tasks createNew(Person person) {
        Tasks add = new Tasks();
        add.setName("New task");
        add.setStatus("IN_DESIGN");
        add.setTaskAuthor(person);
        return repository.save(add);
    }

    @Override
    public TaskDTO loadTask(int id) {
        return mapper.map(repository.findByTaskId(id), TaskDTO.class);
    }

    @Transactional
    public void updateTask(TaskDTO task){
        Tasks updating = repository.findByTaskId(task.getId());
        updating.setName(task.getName());
        updating.setStatus(task.getStatus());
        updating.setDescription(task.getDescription());
    }
}
