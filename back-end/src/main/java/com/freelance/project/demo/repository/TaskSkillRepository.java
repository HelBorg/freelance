package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskSkillRepository extends JpaRepository<TaskSkill, Integer> {

    TaskSkill findByTaskSkillId(int id);

    List<TaskSkill> findAllByTaskId(Task task);

}
