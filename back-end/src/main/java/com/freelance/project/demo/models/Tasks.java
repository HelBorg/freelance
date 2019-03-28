package com.freelance.project.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Tasks implements Serializable {

    @Getter
    public enum TaskStatus {
        PREPARING, PUBLISH, ASSIGNED, IN_WORK, DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private int taskId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "assigned_user", referencedColumnName = "person_id")
    private Person assignedUser;

    @ManyToMany
    @JoinTable(name = "skills",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skills> taskSkills;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "person",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> candidatesList;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "person_id")
    private Person taskAuthor;

    @OneToMany(mappedBy = "task")
    private List<Reviews> taskReviews;

    public Tasks() {
    }


    public Tasks(String name, String description, Date deadline, TaskStatus status,
                 Person assignedUser, List<Skills> taskSkills, List<Person> candidatesList,
                 Person taskAuthor, List<Reviews> taskReviews) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.assignedUser = assignedUser;
        this.taskSkills = taskSkills;
        this.candidatesList = candidatesList;
        this.taskAuthor = taskAuthor;
        this.taskReviews = taskReviews;
    }
}
