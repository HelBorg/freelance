package com.freelance.project.demo.repository.specifications;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private Integer id; //for skills
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, Integer id, String operation, Object value) {
        this.key = key;
        this.id = id;
        this.operation = operation;
        this.value = value;
    }
}
