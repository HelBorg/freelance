package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Skill;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskDTO {

    int id;
    String name;
    String description;
    String status;
    Date createdTime;
    List<Skill> skills;
    //private Date deadline;
   // private Person assignedUser;
}
