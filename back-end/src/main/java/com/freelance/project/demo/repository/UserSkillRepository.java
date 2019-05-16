package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    UserSkill findByUserSkillId(int id);

    List<UserSkill> findAllByPersonId (Person person);


}
