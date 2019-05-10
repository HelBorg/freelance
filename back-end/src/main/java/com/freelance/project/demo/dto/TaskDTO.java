package com.freelance.project.demo.dto;


import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class TaskDTO {
    int id;
    String name;
    String description;
    String status;
    Date createdTime;
    Date deadline;
    List<TaskSkillDTO> skills;
    List<ReviewDTO> reviews;
    PersonDTO author;
    PersonDTO assignedUser;
}
