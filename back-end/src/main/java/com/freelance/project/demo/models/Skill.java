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
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private int skillId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "skillId", cascade = CascadeType.ALL)
    private List<TaskSkill> taskSkills;


    @ManyToMany(mappedBy = "skillId", cascade = CascadeType.ALL)
    private List<UserSkill> userSkills;


}