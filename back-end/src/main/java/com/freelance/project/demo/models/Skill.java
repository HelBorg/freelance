package com.freelance.project.demo.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private int skillId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "skillId")
    private List<TaskSkill> taskSkills;


    @ManyToMany(mappedBy = "skillId")
    private List<UserSkill> userSkills;

    public Skill(){

    }
    public Skill(String name, List<TaskSkill> taskSkills, List<UserSkill> userSkills) {
        this.name = name;
        this.taskSkills = taskSkills;
        this.userSkills = userSkills;
    }


}