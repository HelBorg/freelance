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
    private Filter filter;

    public PageAndSort() {
    }

    public PageAndSort(int personId, String sort, int currentPage, int pageSize, String pageName) {
        this.personId = personId;
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageName = pageName;
    }

    public PageAndSort(int personId, String pageName, String sort, int currentPage, int pageSize, Filter filter) {
        this.personId = personId;
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageName = pageName;
        this.filter = filter;
    }
}
