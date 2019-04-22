package com.freelance.project.demo.controller;


import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.service.TaskSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/task-skill")
public class TaskSkillController{

    @Autowired
    private TaskSkillService taskSkillService;

    @PostMapping
    public void createNewSkillsList(@RequestBody TaskSkillDTO taskSkillDTO){
        taskSkillService.addNewTaskSkill(taskSkillDTO);
    }
}
