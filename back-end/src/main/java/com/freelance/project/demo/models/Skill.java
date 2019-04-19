package com.freelance.project.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long skillId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "taskSkills")
    private List<Task> taskSkills;

    @ManyToMany(mappedBy = "skills")
    private List<UserSkill> userSkills;

    public Skill() {
    }

    public Skill(String name, List<Task> taskSkills, List<UserSkill> userSkills) {
        this.name = name;
        this.taskSkills = taskSkills;
        this.userSkills = userSkills;
    }
}
