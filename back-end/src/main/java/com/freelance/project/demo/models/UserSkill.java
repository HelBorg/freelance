package com.freelance.project.demo.models;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "person_skill")
public class UserSkill implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_skill_id", nullable = false)
    private int userSkillId;

    @Column(name = "level", nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skillId;


}
