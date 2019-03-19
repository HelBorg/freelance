package com.freelance.project.demo.dao.impl;

import com.freelance.project.demo.dao.IGenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/* Creator - Dmitry Nesterov

   This class Override CRUD operations

   Constructor GenericDaoImpl() is using for ability of getting generic class parameters

 */
public abstract class GenericDaoImpl<T> implements IGenericDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> genericClass;

    GenericDaoImpl() {
        this.genericClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void create(final T t) {
        getEntityManager().persist(t);
    }

    @Override
    public void update(final T t) {
        getEntityManager().merge(t);
    }

    @Override
    public void delete(final Object id) {
        getEntityManager().remove(getEntityManager().getReference(genericClass, id));
    }

    @Override
    public List<T> findAll() {
        return getEntityManager()
                .createQuery("Select t from " + genericClass.getSimpleName() + " t")
                .getResultList();
    }

    @Override
    public T find(final Object id) {
        return (T) getEntityManager().find(genericClass, id);


    }
}

