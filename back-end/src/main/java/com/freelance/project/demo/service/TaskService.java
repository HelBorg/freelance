package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface TaskService {

    Task createNew(Person person);

    void deleteTask(int id);

    TaskDTO loadTask(int id);

    void updateTask(TaskDTO task);

    String updateStatus(int id, String status);

    void updateAssignedUser(int personId, int taskId);

    void deleteAssignAndRevertStatus(int taskId);

    Page<Task> getByAuthorId(PageRequest request, Filter filter);

    Page<Task> getByAssignedUserId(PageRequest request, Filter filter);

    Page<Task> getByCandidateId(PageRequest request, Filter filter);

    Page<Task> findAll(PageRequest request, Filter filter);

}
