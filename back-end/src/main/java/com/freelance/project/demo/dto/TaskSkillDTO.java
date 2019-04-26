package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.models.Task;
import lombok.Data;

@Data
public class TaskSkillDTO {

    int id;
    String level;
    SkillDTO skillName;

}
