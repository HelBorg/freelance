package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();

    List<TaskDTO> findAllByAuthor(Long author_id);

    List<TaskDTO> findAllByCandidate(Long candidate_id);
}
