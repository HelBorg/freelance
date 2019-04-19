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

    public PageAndSort getPageAndSort() {
        return pageAndSort;
    }

    public void setPageAndSort(PageAndSort pageAndSort) {
        this.pageAndSort = pageAndSort;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }
}
