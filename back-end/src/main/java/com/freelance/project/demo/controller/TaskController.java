package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskServiceImpl service;


    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<TaskDTO>> getAllByAuthor(@PathVariable("id") String author_id) {
        long id = Long.parseLong(author_id);
        List<TaskDTO> list = service.findAllByAuthor(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/candidate/{id}")
    public ResponseEntity<List<TaskDTO>> getAllByCandidate(@PathVariable("id") String candidate_id) {
        long id = Long.parseLong(candidate_id);
        List<TaskDTO> list = service.findAllByCandidate(id);
        return ResponseEntity.ok().body(list);
    }
}
