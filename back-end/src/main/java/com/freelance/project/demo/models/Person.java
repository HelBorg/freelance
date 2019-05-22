package com.freelance.project.demo.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "person", indexes = {
        @Index(name = "idx_person_name", columnList = "name")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private int personId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "role", nullable = false)
    private String role = "user";

    @Column(name = "rating")
    private int rating;

    @Column(name = "place_in_rating", nullable = false)
    private int placeInRating;

    @Column(name = "tasks_done")
    private int tasksDone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "userId")
    private List<Review> reviewsAboutUser;

    @OneToMany(mappedBy = "personId")
    private List<UserSkill> userSkills;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "assignedUser")
    private List<Task> assignedTasks;

    @ManyToMany(mappedBy = "candidateTasks")
    private List<Task> candidateTasks;
}
