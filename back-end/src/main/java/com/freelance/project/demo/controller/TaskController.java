package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable int taskId) {
        logger.info("Request to delete task: id - {}", taskId);
        taskService.deleteTask(taskId);
    }

    @DeleteMapping("/{taskId}/assigned")
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

    @PutMapping("/{taskId}/status/{status}")
    public String updateStatus(@PathVariable int taskId, @PathVariable String status) {
        logger.info("Request to update task status: task id - {}, new status - {}", taskId, status);
        return taskService.updateStatus(taskId, status);
    }


    @PutMapping("/{taskId}/assigned/{userId}")
    public void updateAssignedUser(@PathVariable int taskId, @PathVariable int userId) {
        logger.info("Request to update task's assigned user: user id - {}", userId);
        taskService.updateAssignedUser(userId, taskId);
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTaskById(@PathVariable int taskId) {
        logger.info("Request to get task by id: id - {}", taskId);
        return taskService.loadTask(taskId);
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
                                                 @RequestParam("author") Optional<Integer> authorName
    ) throws ParseException {
        Sort sortS = Sort.by(sort.orElse("taskId")).descending();
        if (sortDir.orElse("des").equals("asc")) {
            sortS = sortS.ascending();
        }

        PageRequest request = PageRequest.of(pageNumber.orElse(0),
                pageSize.orElse(5), sortS);
        Page<Task> page;

        switch (pageName.orElse("tasks")) {
            case "candidate":
                page = taskService.getByCandidateId(request, id.orElse(null));
                break;
            case "my":
                page = taskService.getByAuthorId(request, authorName.orElse(null));
                break;
            case "in_work":
                page = taskService.getByAssignedUserId(request, id.orElse(null));
                break;
            default:
                Filter filter = new Filter(id.orElse(0), findName.orElse(""),
                        date_from.orElse("2019-01-01 00:00:00.000"),
                        date_to.orElse(""), due_from.orElse(""),
                        due_to.orElse("3000-01-01 00:00:00.000"),
                        authorName.orElse(null), skillsF.orElse(""), sortS, sortDir.orElse("asc"));
                page = taskService.findAll(filter, request);
                break;
        }
        boolean hasPreviousPage = request.getPageNumber() != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > request.getPageNumber();


        List<TaskDTO> list = page.getContent().stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
        PageAndSort pageAndSort = new PageAndSort(id.orElse(0), pageName.orElse("tasks"), sortS,
                pageNumber.orElse(0), pageSize.orElse(5));
        logger.info("Request to get tasks: {}", page);

        return ResponseEntity.ok().body(new Pager<>(list, hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort));
    }

    private Date dateConstructor(String date) throws ParseException {
        return date.length() > 0 ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .parse(date.replace("T", " ").replace("Z", ""))
                : new Date();
    }


}
