package com.freelance.project.demo.models;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "task_skill")
public class TaskSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_skill_id", nullable = false)
    private int taskSkillId;

    // @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skillId;
}
