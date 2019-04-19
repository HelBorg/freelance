package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    Tasks findByTaskId(int id);
}
