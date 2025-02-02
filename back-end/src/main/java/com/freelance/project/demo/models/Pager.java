package com.freelance.project.demo.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pager<T> implements Serializable {

    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int pagesCount;
    private List<T> items;
    PageAndSort pageAndSort;


    public Pager(List<T> items, boolean hasPreviousPage, boolean hasNextPage, int pagesCount, PageAndSort pageAndSort) {
        this.items = items;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
        this.pagesCount = pagesCount;
        this.pageAndSort = pageAndSort;
    }
}
