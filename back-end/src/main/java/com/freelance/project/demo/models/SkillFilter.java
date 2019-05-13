package com.freelance.project.demo.models;

import javafx.util.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SkillFilter {
    private String name;
    private Integer value;
    private String valueS;

    public SkillFilter(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public SkillFilter(String name, String value) {
        this.name = name;
        this.valueS = value;
    }
}
