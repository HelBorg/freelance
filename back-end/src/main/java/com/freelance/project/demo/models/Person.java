package com.freelance.project.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "person")
public class Person implements Serializable {

    @Getter
    public enum UserRole {
        ADMIN, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private int personId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "userReviews")
    private List<Reviews> reviewsAboutUser;

    @OneToMany(mappedBy = "personSkills")
    private List<UserSkills> userSkills;

    @OneToOne(mappedBy = "taskAuthor")
    private Tasks createdTasks;

    @OneToMany(mappedBy = "assignedUser")
    private List<Tasks> assignedTasks;

    @ManyToMany(mappedBy = "candidatesList")
    private List<Tasks> candidateTasks;

    @OneToMany(mappedBy = "senderId")
    private List<Message> sendedMsgs;

    @OneToMany(mappedBy = "receiverId")
    private List<Message> recieveMsgs;

    public Person() {
    }

    public Person(String name, UserRole role, int rating, String email, List<Reviews> reviewsAboutUser,
                  List<UserSkills> userSkills, Tasks createdTasks, List<Tasks> assignedTasks,
                  List<Tasks> candidateTasks, List<Message> sendedMsgs, List<Message> recieveMsgs) {
        this.name = name;
        this.role = role;
        this.rating = rating;
        this.email = email;
        this.reviewsAboutUser = reviewsAboutUser;
        this.userSkills = userSkills;
        this.createdTasks = createdTasks;
        this.assignedTasks = assignedTasks;
        this.candidateTasks = candidateTasks;
        this.sendedMsgs = sendedMsgs;
        this.recieveMsgs = recieveMsgs;
    }
}
