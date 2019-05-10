package com.freelance.project.demo.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageAndSort implements Serializable {
    private int personId; //extra parameter
    private String sort;
    private int currentPage;
    private int pageSize;
    private String pageName;

    public PageAndSort() {
    }

    public PageAndSort(int personId, String sort, int currentPage, int pageSize, String pageName) {
        this.personId = personId;
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageName = pageName;
    }
}
