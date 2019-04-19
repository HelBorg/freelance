package com.freelance.project.demo.models;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "person_skill")
public class UserSkill implements Serializable {

    @Getter
    public enum SkillLevel {
        BAD, SEMI_GOOD, GOOD, SEMI_PROFI, PROFI
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_skill_id", nullable = false)
    private int userSkillId;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personSkills;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "person_skill",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;


}
