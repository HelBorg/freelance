package com.freelance.project.demo.dao.impl;


import com.freelance.project.demo.dao.IPersonDao;
import com.freelance.project.demo.models.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl extends GenericDaoImpl<Person> implements IPersonDao {
}
