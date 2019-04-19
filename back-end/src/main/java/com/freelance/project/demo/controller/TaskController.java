package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    private TaskServiceImpl service;


//    @GetMapping
//    public ResponseEntity<List<TaskDTO>> getAll() {
//        return ResponseEntity.ok().body(service.findAll());
//    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<TaskDTO>> getAllByAuthor(@PathVariable("id") String author_id) {
        int id = Integer.parseInt(author_id);
        List<TaskDTO> list = service.findAllByAuthor(id);
        return ok().body(list);
    }

//    @GetMapping("/candidate/{id}")
//    public ResponseEntity<List<TaskDTO>> getAllByCandidate(@PathVariable("id") String candidate_id) {
//        int id = Integer.parseInt(candidate_id);
//        List<TaskDTO> list = service.findAllByCandidate(id);
//        return ResponseEntity.ok().body(list);
//    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam("size") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> pageNumber) {
        Map<Object, Object> map = new HashMap<>();

        service.findSorted(Optional.of(1),  Optional.of(2));
        map.put("1", service.findSorted(pageSize, pageNumber));
        return ok(map);
    }

}
