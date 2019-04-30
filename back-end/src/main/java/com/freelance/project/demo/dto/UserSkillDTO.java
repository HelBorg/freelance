package com.freelance.project.demo.dto;

import lombok.Data;

@Data
public class UserSkillDTO {

    private int id;
    private int level;
    private SkillDTO skillName;
}
