package com.freelance.project.demo.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageAndSort implements Serializable {
    private String sort;
    private int currentPage;
    private int pageSize;
    private String find;

    public PageAndSort() {
    }

    public PageAndSort(String sort, int currentPage, int pageSize, String find) {
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.find = find;
    }

}
