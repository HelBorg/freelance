package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.UserSkillDTO;

public interface UserSkillService {

    void deleteTaskSkill(int id);
    void addNewUserSkill(UserSkillDTO userSkillDTO, int personId);
}
