package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
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
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

    @Autowired
    private DozerBeanMapper mapper;

    protected static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        taskService.deleteTask(id);
    }


    @PostMapping("/new")
    public ResponseEntity create(@AuthenticationPrincipal UserDetails userDetails){
        Task createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());
        return ok(model);
    }

    @PostMapping("/update/status/{id}/{status}")
    public String updateStatus(@PathVariable int id, @PathVariable String status){
        return taskService.updateStatus(id,status);
    }


    @PostMapping("/update/assigned/{taskId}/{userId}")
    public void updateAssignedUser(@PathVariable int taskId, @PathVariable int userId){
        taskService.updateAssignedUser(userId,taskId);
    }

    @PostMapping("/delete/assigned/{taskId}")
    public void deleteAssignAndRevertStatus(@PathVariable int taskId){
        taskService.deleteAssignAndRevertStatus(taskId);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable int id) {
       return taskService.loadTask(id);

    }


    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("id") Optional<Integer> id,
                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                 @RequestParam("sort") Optional<String> sort,
                                                 @RequestParam("pageName") Optional<String> pageName) {
        Pager<TaskDTO> pager = taskService.findAll(id,pageSize, pageNumber, sort, pageName);
        logger.info("Request to get tasks: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

    @PostMapping
    public void updateTask(@RequestBody TaskDTO task){
        System.out.println(task);
        taskService.updateTask(task);
    }

}
