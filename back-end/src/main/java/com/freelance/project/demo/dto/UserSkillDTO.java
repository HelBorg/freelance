package com.freelance.project.demo.dto;

import lombok.Data;

@Data
public class UserSkillDTO {

    private int id;
    private String level;
    private SkillDTO skillName;
}
