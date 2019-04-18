package com.freelance.project.demo.dto;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Review;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.models.Task;

import java.sql.Date;
import java.util.List;

public class TaskDTO {
    private int taskId;

    private String name;

    private String description;

    private Date deadline;

    private Task.TaskStatus status;

    private Person assignedUser;

    private List<Skill> taskSkills;

    private List<Person> candidatesList;

    private Person taskAuthor;

    private List<Review> taskReviews;
}
