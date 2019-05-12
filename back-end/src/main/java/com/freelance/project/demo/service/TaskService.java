package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import org.springframework.data.domain.Sort;

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

    Pager<TaskDTO> findAll(PageAndSort pageAndSort);

}
