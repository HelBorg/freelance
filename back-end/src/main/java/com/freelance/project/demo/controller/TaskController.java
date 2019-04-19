package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        Tasks createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());
        return ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable int id){
        Map<Object, Object> model = new HashMap<>();
        model.put("task",taskService.loadTask(id));
        return ok(model);
    }

    @PostMapping
    public void updateTask(@RequestBody TaskDTO task){
        System.out.println("TTTTTTTTask id = "+ task.getId());
        System.out.println("TTTTTTTTask name = " +task.getName());
        taskService.updateTask(task);
    }

}
