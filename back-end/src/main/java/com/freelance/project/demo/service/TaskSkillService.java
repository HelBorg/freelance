package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskSkillDTO;


public interface TaskSkillService {

    void deleteTaskSkill(int id);

    void addNewTaskSkill(TaskSkillDTO taskSkillDTO, int taskId);

}
