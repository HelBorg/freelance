package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createNew(Person person);

    void deleteTask(int id);

    TaskDTO loadTask(int id);

    void updateTask(TaskDTO task);

    String updateStatus(int id, String status);

    void updateAssignedUser(int personId, int taskId);

    Pager<TaskDTO> findAll(Optional<Integer> id,
                     Optional<Integer> pageSize,
                     Optional<Integer> pageNumber,
                     Optional<String> pageSort,
                     Optional<String> pageName);

}
