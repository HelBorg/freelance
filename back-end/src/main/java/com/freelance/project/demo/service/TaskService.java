package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;

import java.util.List;
import java.util.Optional;

public interface TaskService {
//    List<TaskDTO> findAll();

    Task createNew(Person person);

    TaskDTO loadTask(int id);

    void updateTask(TaskDTO task);


    Pager<TaskDTO> findAll(Optional<Integer> id,
                     Optional<Integer> pageSize,
                     Optional<Integer> pageNumber,
                     Optional<String> pageSort,
                     Optional<String> pageName);

}
