package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.models.TaskSkill;


public interface TaskSkillService {

    TaskSkill addNewTaskSkill(TaskSkillDTO taskSkillDTO);

}
