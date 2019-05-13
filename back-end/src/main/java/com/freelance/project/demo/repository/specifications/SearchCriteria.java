package com.freelance.project.demo.repository.specifications;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String field; //for skills
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, String field, String operation, Object value) {
        this.key = key;
        this.field = field;
        this.operation = operation;
        this.value = value;
    }
}
