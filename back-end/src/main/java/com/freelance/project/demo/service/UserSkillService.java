package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.UserSkillDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.UserSkill;

import java.util.List;

public interface UserSkillService {

    void deleteTaskSkill(int id);

    void addNewUserSkill(UserSkillDTO userSkillDTO, int personId);

    List<UserSkillDTO> findAllForPerson (int personId);

}
