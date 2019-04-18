package com.freelance.project.demo.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    int personId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "role", nullable = false)
    private String role ="user";

    @Column(name = "rating")
    private int rating;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "userReviews")
    private List<Review> reviewsAboutUser;

    @OneToMany(mappedBy = "personSkills")
    private List<UserSkills> userSkills;

    @OneToMany(mappedBy = "taskAuthor")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "assignedUser")
    private List<Task> assignedTasks;

    @ManyToMany(mappedBy = "candidatesList")
    private List<Task> candidateTasks;


}
