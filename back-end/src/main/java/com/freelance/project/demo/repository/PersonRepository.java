package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByPersonId(int id);
    Person findByEmail(String email);

    @Query("Select t from Person t")
    Page<Person> find(Pageable pageRequest);
}
