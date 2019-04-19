package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Person;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {

    int id;
    String name;
    String description;
    String status;

    private Date deadline;

    private Person assignedUser;

}
