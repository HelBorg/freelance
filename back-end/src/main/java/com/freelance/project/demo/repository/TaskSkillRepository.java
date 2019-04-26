package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.TaskSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskSkillRepository extends JpaRepository<TaskSkill, Integer> {

    TaskSkill findByTaskSkillId(int id);


}
