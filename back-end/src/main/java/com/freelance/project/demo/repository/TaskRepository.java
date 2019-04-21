package com.freelance.project.demo.repository;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Collection<Task> findAllByAuthor_PersonId(int author_id);

    //    Page<Task> findAll(PageRequest pageRequest);

    List<Task> findAll();

//    Collection<Task> findAllByCandidateId(int );

//    Page<Task> findByName(String );

    Task findByTaskId(int id);
}
