package com.freelance.project.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class Pager<T> {
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int pagesCount;
    private List<T> tasks;

    PageAndSort pageAndSort;

    public Pager() {
    }

    public Pager(List<T> tasks, boolean hasPreviousPage, boolean hasNextPage, int pagesCount, PageAndSort pageAndSort) {
        this.tasks = tasks;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
        this.pagesCount = pagesCount;
        this.pageAndSort = pageAndSort;
    }

}
