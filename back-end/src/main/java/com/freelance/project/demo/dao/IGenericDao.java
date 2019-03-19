package com.freelance.project.demo.dao;

import java.util.List;

public interface IGenericDao<T> {

    void create(T t);

    void update(T t);

    void delete(Object id);

    T find(Object id);

    List<T> findAll();
}
