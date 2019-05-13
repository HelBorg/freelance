package com.freelance.project.demo.repository;


import com.freelance.project.demo.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r join r.taskId tr where tr.taskId = :taskId and r.parentId is null and r.done=false order by r.dateTime desc")
    List<Review> findAllForTask(@Param("taskId") int taskId);

    @Query("select r from Review r join r.taskId tr where tr.assignedUser.personId = :userId and r.done=true order by r.dateTime desc  ")
    List<Review> findAllAboutUser(@Param("userId") int userId);

    @Query("select r from Review r  where r.parentId = :reviewParentId order by r.dateTime asc ")
    List<Review> findAllSubComments(@Param("reviewParentId") int reviewParentId);
}
