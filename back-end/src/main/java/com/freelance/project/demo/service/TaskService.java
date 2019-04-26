package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> findAll();

    List<TaskDTO> findAllByAuthor(int author_id);

    Task createNew(Person person);

    void deleteTask(int id);

    TaskDTO loadTask(int id);
//    List<TaskDTO> findAllByCandidate(int candidate_id);

    void updateTask(TaskDTO task);

    String updateStatus(int id, String status);

    void updateAssignedUser(int personId, int taskId);

    Pager findSorted(Optional<Integer> pageSize, Optional<Integer> pageNumber);
    // Pager findSorted(PageAndSort pageAndSort);
}
