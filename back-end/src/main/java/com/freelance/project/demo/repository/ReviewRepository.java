package com.freelance.project.demo.repository;


import com.freelance.project.demo.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r join r.taskId tr where tr.taskId = :taskId")
    List<Review> findAllForTask(@Param("taskId") int taskId);
}
