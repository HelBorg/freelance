package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByPersonId(int id);

    Person findByEmail(String email);

    @Query("select p from Person p where p.name like %:name%")
    Page<Person> findByName(@Param("name") String name, Pageable pageable);

    @Query("select count(p) from Person p where p.rating > :rating")
    int countRaiting(@Param("rating") int rating);

    long count();

}
