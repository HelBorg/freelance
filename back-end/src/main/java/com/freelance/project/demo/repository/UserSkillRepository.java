package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.TaskSkill;
import com.freelance.project.demo.models.UserSkill;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    UserSkill findByUserSkillId(int id);

}
