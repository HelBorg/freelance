package com.freelance.project.demo.models;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Data
public class PageAndSort implements Serializable {

    private Sort sort;
    private int currentPage;
    private int pageSize;
    private String findName;
    private int personId; //extra parameter

    public PageAndSort(int personId, String sort, String sortDir, int currentPage, int pageSize) {
        this.personId = personId;
        this.sort = Sort.by(sort).descending();
        if (sortDir.equals("asc")) {this.sort = this.sort.ascending();}
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PageAndSort(String sort, String sortDir, int currentPage, int pageSize, String findName) {
        this.sort = Sort.by(sort).descending();
        if (sortDir.equals("asc")) {this.sort = this.sort.ascending();}
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.findName = findName;
    }

    public PageAndSort(String sort, String sortDir, int currentPage, int pageSize) {
        this.sort = Sort.by(sort).descending();
        if (sortDir.equals("asc")) {this.sort = this.sort.ascending();}
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
