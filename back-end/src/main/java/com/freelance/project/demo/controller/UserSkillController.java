package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.UserSkillDTO;
import com.freelance.project.demo.service.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-skill")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @DeleteMapping("/{id}")
    public void deleteTaskSkill(@PathVariable int id){
        userSkillService.deleteTaskSkill(id);
    }

    @PostMapping("/{personId}")
    public void createNewSkill(@RequestBody UserSkillDTO userSkillDTO, @PathVariable int personId){
        userSkillService.addNewUserSkill(userSkillDTO, personId);
    }
}
