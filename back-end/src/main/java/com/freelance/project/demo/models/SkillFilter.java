package com.freelance.project.demo.models;

import lombok.Data;

@Data
public class SkillFilter {
    private String name;
    private int id;
    private Integer value;
    private String valueS;

    public SkillFilter(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public SkillFilter(int id, String value) {
        this.id = id;
        this.valueS = value;
    }

    public SkillFilter(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public SkillFilter(String name, String value) {
        this.name = name;
        this.valueS = value;
    }
}
