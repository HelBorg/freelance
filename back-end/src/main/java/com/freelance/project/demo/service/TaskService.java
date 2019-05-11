package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.Pager;

import java.util.Date;
import java.util.Optional;

public interface TaskService {

    Task createNew(Person person);

    void deleteTask(int id);

    TaskDTO loadTask(int id);

    void updateTask(TaskDTO task);

    String updateStatus(int id, String status);

    void updateAssignedUser(int personId, int taskId);

    void deleteAssignAndRevertStatus(int taskId);

    Pager<TaskDTO> findAll(int id,
                           int pageSize,
                           int pageNumber,
                           String pageSort,
                           String pageName,
                           String find_name,
                           Date date_from,
                           Date date_to,
                           Date due_from,
                           Date due_to);

}
