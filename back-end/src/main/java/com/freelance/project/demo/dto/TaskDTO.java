package com.freelance.project.demo.dto;


import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class TaskDTO {

    int id;
    String name;
    String description;
    String status;
    Date createdTime;
    Date deadline;
    Collection<TaskSkillDTO> skills;
    Collection<ReviewDTO> reviews;
    PersonDTO author;
    PersonDTO assignedUser;
}
