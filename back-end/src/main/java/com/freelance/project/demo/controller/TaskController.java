package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.SkillF;
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

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;


    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        logger.info("Request to delete task: id - {}", id);
        taskService.deleteTask(id);
    }


    @PostMapping("/new")
    public ResponseEntity create(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Request to create task from user: {}", userDetails);
        Task createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());
        return ok(model);
    }

    @PostMapping("/update/status/{id}/{status}")
    public String updateStatus(@PathVariable int id, @PathVariable String status) {
        logger.info("Request to update task status: task id - {}, new status - {}", id, status);
        return taskService.updateStatus(id, status);
    }


    @PostMapping("/update/assigned/{taskId}/{userId}")
    public void updateAssignedUser(@PathVariable int taskId, @PathVariable int userId) {
        logger.info("Request to update task's assigned user: user id - {}", userId);
        taskService.updateAssignedUser(userId, taskId);
    }

    @PostMapping("/delete/assigned/{taskId}")
    public void deleteAssignAndRevertStatus(@PathVariable int taskId) {
        taskService.deleteAssignAndRevertStatus(taskId);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable int id) {
        logger.info("Request to get task by id: id - {}", id);
        return taskService.loadTask(id);
    }

    private Date dateConstructor(String date) throws ParseException {
        Date d = date.length() > 0 ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .parse(date.replace("T", " ").replace("Z", ""))
                : new Date();
        return d;
    }

    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("id") Optional<Integer> id,
                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                 @RequestParam("sort") Optional<String> sort,
                                                 @RequestParam("pageName") Optional<String> pageName,
                                                 @RequestParam("find_name") Optional<String> findName,
                                                 @RequestParam("date_from") Optional<String> date_from,
                                                 @RequestParam("date_to") Optional<String> date_to,
                                                 @RequestParam("due_from") Optional<String> due_from,
                                                 @RequestParam("due_to") Optional<String> due_to,
                                                 @RequestParam("skillsF") Optional<List<String>> skillF,
                                                 @RequestParam("author") Optional<String> authorName
            /* @RequestParam("skills") Optional<List<Pair<String, Integer>>> skillsList*/) throws ParseException {
        int idN = id.orElse(0);
        int size = pageSize.orElse(5);
        int pageId = pageNumber.orElse(0);
        String pageSort = sort.orElse("taskId");
        String pageN = pageName.orElse("tasks");
        //Filter
        String name = findName.orElse("");
        Date from = dateConstructor(date_from.orElse("2019-01-01 00:00:00.000"));
        Date to = dateConstructor(date_to.orElse(""));
        Date dueFrom = dateConstructor(due_from.orElse(""));
        Date dueTo = dateConstructor(due_to.orElse("3000-01-01 00:00:00.000"));
        logger.info("from {}", skillF);

        Pager<TaskDTO> pager = taskService.findAll(idN, size, pageId, pageSort, pageN, name, from, to, dueFrom, dueTo);
        logger.info("Request to get tasks: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

    @PostMapping
    public void updateTask(@RequestBody TaskDTO task) {
        logger.info("Request to update task: {}", task);
        taskService.updateTask(task);
    }
}
