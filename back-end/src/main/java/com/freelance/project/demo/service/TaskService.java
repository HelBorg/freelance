package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> findAll();

    List<TaskDTO> findAllByAuthor(Long author_id);

    List<TaskDTO> findAllByCandidate(Long candidate_id);

    Pager findSorted(Optional<Integer> pageSize, Optional<Integer> pageNumber);
    Pager findSorted(PageAndSort pageAndSort);
}
