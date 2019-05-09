package com.freelance.project.demo.models;


import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private int reviewId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "done")
    private boolean done;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime", nullable = false)
    private Date dateTime;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id")
    private Person userId;

   @ManyToOne(cascade = {CascadeType.ALL})
   @JoinColumn(name = "task_id")
   private Task taskId;



}