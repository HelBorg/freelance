package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.service.impl.TaskServiceImpl;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

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
    public ResponseEntity<List<TaskDTO>> getAllByAuthor(@PathVariable("id") String author_id) {
        int id = Integer.parseInt(author_id);
        List<TaskDTO> list = taskService.findAllByAuthor(id);
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

        taskService.findSorted(Optional.of(1),  Optional.of(2));
        map.put("1", taskService.findSorted(pageSize, pageNumber));
        return ok(map);
    }

}
