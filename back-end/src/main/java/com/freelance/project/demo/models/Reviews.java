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
@Table(name = "reviews")
public class Reviews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private int reviewId;

    @Column(name = "description", nullable = false)
    private String decription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime", nullable = false)
    private Date dateTime;

    @ManyToMany
    @JoinTable(name = "user_reviews",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> userReviews;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "task_id")
    private Tasks task;

    public Reviews() {
    }

    public Reviews(String decription, Date dateTime, List<Person> userReviews, Tasks task) {
        this.decription = decription;
        this.dateTime = dateTime;
        this.userReviews = userReviews;
        this.task = task;
    }
}
