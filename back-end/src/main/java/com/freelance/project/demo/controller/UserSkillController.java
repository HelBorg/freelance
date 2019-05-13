package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.UserSkillDTO;
import com.freelance.project.demo.service.UserSkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-skill")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    private static final Logger logger = LoggerFactory.getLogger(UserSkillController.class);


    @DeleteMapping("/{id}")
    public void deleteTaskSkill(@PathVariable int id){
        userSkillService.deleteTaskSkill(id);
    }

    @PostMapping("/{personId}")
    public void createNewSkill(@RequestBody UserSkillDTO userSkillDTO, @PathVariable int personId){
        userSkillService.addNewUserSkill(userSkillDTO, personId);
    }
}
