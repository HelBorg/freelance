package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.TaskSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface TaskSkillRepository extends JpaRepository<TaskSkill, Integer> {

    TaskSkill findByTaskSkillId(int id);

}
