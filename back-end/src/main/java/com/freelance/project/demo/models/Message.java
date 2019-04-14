package com.freelance.project.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Getter
    public enum MessageStatus {
        INBOX, OUTBOX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id", nullable = false)
    private int messageId;

    @Column(name = "message", nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime", nullable = false)
    private Date dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "person_id")
    private Person senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "person_id")
    private Person receiverId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Tasks taskId;

    public Message() {
    }

    public Message(String message, Date dateTime, MessageStatus status,
                   Person senderId, Person receiverId, Tasks taskId) {
        this.message = message;
        this.dateTime = dateTime;
        this.status = status;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.taskId = taskId;
    }
}