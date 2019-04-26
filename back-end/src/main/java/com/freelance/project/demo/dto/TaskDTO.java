package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Review;
import com.freelance.project.demo.models.TaskSkill;
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
    Collection<TaskSkillDTO> skills;
    Collection<ReviewDTO> reviews;
    PersonDTO author;
    PersonDTO assignedUser;
}
