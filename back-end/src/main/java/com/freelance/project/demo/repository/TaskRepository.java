package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Collection<Task> findAll();

    Collection<Task> findAllByAuthorId(Long author_id);

    Collection<Task> findAllByCandidateId(Long candidate_id);
}
