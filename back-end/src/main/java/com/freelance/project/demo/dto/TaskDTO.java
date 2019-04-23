package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Person;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskDTO implements Serializable {

    private int id;
    private String name;
    private String description;
    private String status;
    private Date deadline;

}
