package com.freelance.project.demo.models;

import lombok.Data;

@Data
public class SkillFilter {
    private int id;
    private Integer value;

    public SkillFilter(int id, int value) {
        this.id = id;
        this.value = value;
    }
}
