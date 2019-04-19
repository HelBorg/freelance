package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> findAllByAuthor_PersonId(Long author_id);

    Collection<Task> findAll(Pageable pageable);

    List<Task> findAll();

    Collection<Task> findAllByCandidateId(Long candidate_id);

    Page<Task> findByName(String name, Pageable pageRequest);

    Page<Task> findByName(String name, PageRequest pageRequest);
}
