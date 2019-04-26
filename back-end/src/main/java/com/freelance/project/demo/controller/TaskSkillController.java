package com.freelance.project.demo.controller;


import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.service.TaskSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1/task-skill")
public class TaskSkillController{

    @Autowired
    private TaskSkillService taskSkillService;

    @DeleteMapping("/{id}")
    public void deleteTaskSkill(@PathVariable int id){
        taskSkillService.deleteTaskSkill(id);
    }

    @PostMapping("/{taskId}")
    public void createNewSkillsList(@RequestBody TaskSkillDTO taskSkillDTO, @PathVariable int taskId){
        taskSkillService.addNewTaskSkill(taskSkillDTO, taskId);
    }
}
