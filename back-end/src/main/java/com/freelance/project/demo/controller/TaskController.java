package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Filter;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private Filter filter;
    private PageAndSort pageAndSort;

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
        logger.info("Request to save task: {}", task);
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

    public PageRequest buildBeforeGetAll(PageAndSort pageAndSort) {
        return PageRequest.of(pageAndSort.getCurrentPage(), pageAndSort.getPageSize(), pageAndSort.getSort());
    }

    public ResponseEntity<Pager<TaskDTO>> buildAfterGetAll(Page<Task> page, PageRequest request, PageAndSort pageAndSort) {
        boolean hasPreviousPage = request.getPageNumber() != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > request.getPageNumber();
        List<TaskDTO> list = page.getContent().stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
        logger.info("Request to get tasks: {}", page);
        return ResponseEntity.ok().body(new Pager<>(list, hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort));
    }

    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("size") Optional<Integer> pageSize,
                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                 @RequestParam("sort") Optional<String> sort,
                                                 @RequestParam("sortDir") Optional<String> sortDir,
                                                 //Filter
                                                 @RequestParam("find_name") Optional<String> findName,
                                                 @RequestParam("date_from") Optional<String> date_from,
                                                 @RequestParam("date_to") Optional<String> date_to,
                                                 @RequestParam("due_from") Optional<String> due_from,
                                                 @RequestParam("due_to") Optional<String> due_to,
                                                 @RequestParam("skillsFilter") Optional<String> skillsF,
                                                 @RequestParam("author") Optional<Integer> authorName
    ) throws ParseException {
        PageAndSort pageAndSort = new PageAndSort(sort.orElse("time_created"), sortDir.orElse("des"),
                pageNumber.orElse(0), pageSize.orElse(5));
        PageRequest request = buildBeforeGetAll(pageAndSort);
        Page<Task> page = taskService.findAll(request, new Filter(findName.orElse(""),
                date_from.orElse("").equals("") ? "2019-01-01 00:00:00.000" : date_from.get(),
                date_to.orElse(""), due_from.orElse(""),
                due_to.orElse("").equals("") ? "3000-01-01 00:00:00.000" : due_to.get(),
                authorName.orElse(-1), skillsF.orElse("")));
        return buildAfterGetAll(page, request, pageAndSort);
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<Pager<TaskDTO>> getAllByCandidateId(@PathVariable int id,
                                                              @RequestParam("size") Optional<Integer> pageSize,
                                                              @RequestParam("page") Optional<Integer> pageNumber,
                                                              @RequestParam("sort") Optional<String> sort,
                                                              @RequestParam("sortDir") Optional<String> sortDir,
                                                              //Filter
                                                              @RequestParam("find_name") Optional<String> findName,
                                                              @RequestParam("date_from") Optional<String> date_from,
                                                              @RequestParam("date_to") Optional<String> date_to,
                                                              @RequestParam("due_from") Optional<String> due_from,
                                                              @RequestParam("due_to") Optional<String> due_to,
                                                              @RequestParam("skillsFilter") Optional<String> skillsF,
                                                              @RequestParam("author") Optional<Integer> authorName
    ) throws ParseException {
        PageAndSort pageAndSort = new PageAndSort(id,
                sort.orElse("time_created"), sortDir.orElse("des"),
                pageNumber.orElse(0), pageSize.orElse(5));
        PageRequest request = buildBeforeGetAll(pageAndSort);
        Page<Task> page = taskService.getByCandidateId(request, new Filter(id, findName.orElse(""),
                date_from.orElse("").equals("") ? "2019-01-01 00:00:00.000" : date_from.get(),
                date_to.orElse(""), due_from.orElse(""),
                due_to.orElse("").equals("") ? "3000-01-01 00:00:00.000" : due_to.get(),
                authorName.orElse(-1), skillsF.orElse("")));
        return buildAfterGetAll(page, request, pageAndSort);
    }

    @GetMapping("/in_work/{id}")
    public ResponseEntity<Pager<TaskDTO>> getAllByAssignedUserId(@PathVariable int id,
                                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                                 @RequestParam("sort") Optional<String> sort,
                                                                 @RequestParam("sortDir") Optional<String> sortDir,
                                                                 //Filter
                                                                 @RequestParam("find_name") Optional<String> findName,
                                                                 @RequestParam("date_from") Optional<String> date_from,
                                                                 @RequestParam("date_to") Optional<String> date_to,
                                                                 @RequestParam("due_from") Optional<String> due_from,
                                                                 @RequestParam("due_to") Optional<String> due_to,
                                                                 @RequestParam("skillsFilter") Optional<String> skillsF,
                                                                 @RequestParam("author") Optional<Integer> authorName
    ) throws ParseException {
        PageAndSort pageAndSort = new PageAndSort(id,
                sort.orElse("time_created"), sortDir.orElse("des"),
                pageNumber.orElse(0), pageSize.orElse(5));
        PageRequest request = buildBeforeGetAll(pageAndSort);
        Page<Task> page = taskService.getByAssignedUserId(request, new Filter(id, findName.orElse(""),
                date_from.orElse("").equals("") ? "2019-01-01 00:00:00.000" : date_from.get(),
                date_to.orElse(""), due_from.orElse(""),
                due_to.orElse("").equals("") ? "3000-01-01 00:00:00.000" : due_to.get(),
                authorName.orElse(-1), skillsF.orElse("")));
        return buildAfterGetAll(page, request, pageAndSort);
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<Pager<TaskDTO>> getAllByAuthorId(@PathVariable int id,
                                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                                 @RequestParam("sort") Optional<String> sort,
                                                                 @RequestParam("sortDir") Optional<String> sortDir,
                                                                 //Filter
                                                                 @RequestParam("find_name") Optional<String> findName,
                                                                 @RequestParam("date_from") Optional<String> date_from,
                                                                 @RequestParam("date_to") Optional<String> date_to,
                                                                 @RequestParam("due_from") Optional<String> due_from,
                                                                 @RequestParam("due_to") Optional<String> due_to,
                                                                 @RequestParam("skillsFilter") Optional<String> skillsF,
                                                                 @RequestParam("author") Optional<Integer> authorName
    ) throws ParseException {
        PageAndSort pageAndSort = new PageAndSort(id,
                sort.orElse("time_created"), sortDir.orElse("des"),
                pageNumber.orElse(0), pageSize.orElse(5));
        PageRequest request = buildBeforeGetAll(pageAndSort);
        Page<Task> page = taskService.getByAuthorId(request, new Filter(id, findName.orElse(""),
                date_from.orElse("").equals("") ? "2019-01-01 00:00:00.000" : date_from.get(),
                date_to.orElse(""), due_from.orElse(""),
                due_to.orElse("").equals("") ? "3000-01-01 00:00:00.000" : due_to.get(),
                authorName.orElse(-1), skillsF.orElse("")));
        return buildAfterGetAll(page, request, pageAndSort);
    }
}
