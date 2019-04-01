package com.freelance.project.demo.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter
@Entity
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
    private UserRole role ;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

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


}
