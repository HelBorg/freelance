package com.freelance.project.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_skills")
public class UserSkills implements Serializable {

    @Getter
    public enum SkillLevel {
        BAD, SEMI_GOOD, GOOD, SEMI_PROFI, PROFI
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_skill_id", nullable = false)
    private int userSkillId;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personSkills;

    @ManyToMany
    @JoinTable(name = "user_skills",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skills> skills;


}
