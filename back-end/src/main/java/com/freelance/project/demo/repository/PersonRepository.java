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

    @Query("Select p from Person p")
    Page<Person> find(Pageable pageRequest);

    @Query("select p from Person p where p.name like %:name%")
    Page<Person> findByName(@Param("name") String name, Pageable pageable);


//    @Query("update Person p set p.placeInRating = select ROW_NUMBER() OVER (ORDER BY rating) from Person q where q.personId = p.personId")
//    @Query("Update Person p set p.pl")
//    void updateRaitingPlace();
    //@Query("update User u set u.placeInRating = (select)")
    //@Quary("select rowNumber from Person p ordered by rating")

    long count();

}
