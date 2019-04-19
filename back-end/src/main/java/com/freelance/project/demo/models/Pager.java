package com.freelance.project.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class Pager {
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int pagesCount;
    private List<Task> tasks;

    PageAndSort pageAndSort;

    public Pager() {
    }

    public Pager(List<Task> tasks, boolean hasPreviousPage, boolean hasNextPage, int pagesCount, PageAndSort pageAndSort) {
        this.tasks = tasks;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
        this.pagesCount = pagesCount;
        this.pageAndSort = pageAndSort;
    }

}
