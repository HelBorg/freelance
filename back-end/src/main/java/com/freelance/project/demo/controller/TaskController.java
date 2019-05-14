package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @DeleteMapping("/assigned/{taskId}")
    public void deleteAssignAndRevertStatus(@PathVariable int taskId) {
        logger.info("Request to delete assign and revert status of task: task id - {}", taskId);
        taskService.deleteAssignAndRevertStatus(taskId);
    }

    @PostMapping
    public ResponseEntity create(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Request to create task from user: {}", userDetails);

        Task createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());

        return ok(model);
    }

    @PutMapping
    public void saveTaskData(@RequestBody TaskDTO task) {
        logger.info("Request to update task: {}", task);
        taskService.updateTask(task);
    }

    @PutMapping("/status/{id}/{status}")
    public String updateStatus(@PathVariable int id, @PathVariable String status) {
        logger.info("Request to update task status: task id - {}, new status - {}", id, status);
        return taskService.updateStatus(id, status);
    }


    @PutMapping("/assigned/{taskId}/{userId}")
    public void updateAssignedUser(@PathVariable int taskId, @PathVariable int userId) {
        logger.info("Request to update task's assigned user: user id - {}", userId);
        taskService.updateAssignedUser(userId, taskId);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable int id) {
        logger.info("Request to get task by id: id - {}", id);
        return taskService.loadTask(id);
    }

    private Date dateConstructor(String date) throws ParseException {
        return date.length() > 0 ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .parse(date.replace("T", " ").replace("Z", ""))
                : new Date();
    }

    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("id") Optional<Integer> id,            //user_id if needed
                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                 @RequestParam("sort") Optional<String> sort,
                                                 @RequestParam("sortDir") Optional<String> sortDir,
                                                 @RequestParam("pageName") Optional<String> pageName,
                                                 //Filter
                                                 @RequestParam("find_name") Optional<String> findName,
                                                 @RequestParam("date_from") Optional<String> date_from,
                                                 @RequestParam("date_to") Optional<String> date_to,
                                                 @RequestParam("due_from") Optional<String> due_from,
                                                 @RequestParam("due_to") Optional<String> due_to,
                                                 @RequestParam("skillsFilter") Optional<String> skillsF,
                                                 @RequestParam("author") Optional<String> authorName
    ) throws ParseException {
        //Retrieve data from request parameters and put it into Filter
        JSONArray json = new JSONArray("[" + skillsF.orElse("") + "]");

        List<SkillFilter> skills = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            if (!((JSONObject) json.get(i)).getString("name").equals("")) {
                skills.add(new SkillFilter(((JSONObject) json.get(i)).getString("name"),
                        ((JSONObject) json.get(i)).getInt("value")));
            }
        }

        Date from = dateConstructor(date_from.orElse("").equals("") ?
                "2019-01-01 00:00:00.000" : date_from.orElse(""));
        Date to = dateConstructor(date_to.orElse("").equals("") ?
                "" : date_to.orElse(""));
        Date dueFrom = dateConstructor(due_from.orElse("").equals("") ?
                "" : due_from.orElse(""));
        Date dueTo = dateConstructor(due_to.orElse("").equals("") ?
                "3000-01-01 00:00:00.000" : due_to.orElse(""));

        Sort sortS = Sort.by(sort.orElse("taskId")).descending();
        if (sortDir.orElse("des").equals("asc")) {
            sortS = sortS.ascending();
        }
        Filter filter = new Filter(id.orElse(0), findName.orElse(""), from, to,
                dueFrom, dueTo, authorName.orElse(""), skills, sortS, sortDir.orElse("asc"));
        PageAndSort pageAndSort = new PageAndSort(id.orElse(0), pageName.orElse("tasks"), sortS,
                pageNumber.orElse(0), pageSize.orElse(5));
        Pager<TaskDTO> pager = taskService.findAll(pageAndSort, filter);

        logger.info("Request to get tasks: {}", pager);
        logger.info("Request to filter tasks: {}", filter);

        return ResponseEntity.ok().body(pager);
    }


}
