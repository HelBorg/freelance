package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.UserSkill;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SkillDTO {
    private int skillId;

    private String name;

    private List<TaskDTO> taskSkills;

    private List<UserSkill> userSkills;
}
