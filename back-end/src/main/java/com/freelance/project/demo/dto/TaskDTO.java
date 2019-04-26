package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Skill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TaskDTO implements Serializable {

    private int id;
    private String name;
    private String description;
    private String status;
    private Date deadline;

    private PersonDTO assignedUser;
    private List<SkillDTO> taskSkills;
    private List<PersonDTO> candidateTasks;
    private PersonDTO author;

}
