package com.freelance.project.demo.models;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Data
public class PageAndSort implements Serializable {
    private String sortS;
    private Sort sort;
    private int currentPage;
    private int pageSize;
    private String findName;
    private String pageName; //parameter for tasks
    private int personId; //extra parameter

    public PageAndSort(int personId, String pageName, Sort sort, int currentPage, int pageSize) {
        this.personId = personId;
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageName = pageName;
    }

    public PageAndSort(Sort sort, int currentPage, int pageSize, String findName) {
        this.sort = sort;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.findName = findName;
    }
}
