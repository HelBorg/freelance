package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.models.TaskSkill;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.repository.TaskSkillRepository;
import com.freelance.project.demo.service.TaskSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskSkillServiceImpl implements TaskSkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskSkillRepository taskSkillRepository;


    public TaskSkill addNewTaskSkill(TaskSkillDTO taskSkillDTO) {

        TaskSkill taskSkill = new TaskSkill();
        System.out.println("1111111111111111111111"+taskRepository.findByTaskId(taskSkillDTO.getTaskId()));
        taskSkill.setTaskId(taskRepository.findByTaskId(taskSkillDTO.getTaskId()));
        taskSkill.setLevel(taskSkillDTO.getLevel());
        System.out.println("12312312312312312312313"+skillRepository.findSkillByName(taskSkillDTO.getSkillName()));
        taskSkill.setSkillId(skillRepository.findSkillByName(taskSkillDTO.getSkillName()));
        System.out.println(taskSkillDTO);
        return taskSkillRepository.save(taskSkill);

    }
}
