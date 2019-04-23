package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.service.impl.TaskServiceImpl;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

    protected static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/new")
    public ResponseEntity create(@AuthenticationPrincipal UserDetails userDetails){
        Task createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());
        return ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable int id) {
        Map<Object, Object> model = new HashMap<>();
        model.put("task", taskService.loadTask(id));
        return ok(model);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Pager<Task>> getAllByAuthor(@PathVariable("id") String author_id,
                                                  @RequestParam("size") Optional<Integer> pageSize,
                                                   @RequestParam("page") Optional<Integer> pageNumber,
                                                @RequestParam("sort") Optional<String> sort){
        int id = Integer.parseInt(author_id);
        return ResponseEntity.ok().body(taskService.findAllByAuthor(id, pageSize, pageNumber, sort));
    }

    @GetMapping("/candidate/{id}")
    public ResponseEntity<Pager<Task>> getAllByCandidate(@PathVariable("id") String candidate_id,
                                                   @RequestParam("size") Optional<Integer> pageSize,
                                                   @RequestParam("page") Optional<Integer> pageNumber,
                                                   @RequestParam("sort") Optional<String> sort) {
        int id = Integer.parseInt(candidate_id);
        return ResponseEntity.ok().body(taskService.findAllByCandidate(id, pageSize, pageNumber, sort));
    }

    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("size") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> pageNumber,
                                        @RequestParam("sort") Optional<String> sort) {
        Pager<TaskDTO> pager = taskService.findAll(pageSize, pageNumber, sort);
//        logger.info("Request to get tasks: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

}
