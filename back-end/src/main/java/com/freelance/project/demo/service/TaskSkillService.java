package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;

import java.util.List;


public interface TaskSkillService {

    void deleteTaskSkill(int id);

    void addNewTaskSkill(TaskSkillDTO taskSkillDTO, int taskId);

    List<TaskSkillDTO> findAllForTask(int taskId);


}
