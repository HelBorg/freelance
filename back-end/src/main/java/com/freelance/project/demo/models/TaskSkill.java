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
    private int level;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private Task taskId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id")
    private Skill skillId;
}
