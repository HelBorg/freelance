package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.controller.TaskController;
import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DozerBeanMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    public void deleteTask(int id) {
        taskRepository.delete(taskRepository.findByTaskId(id));
    }

    public String updateStatus(int id, String status) {
        String nextStatus = selectNextTaskStatus(status);
        taskRepository.updateStatus(id, nextStatus);
        return nextStatus;
    }

    public void updateAssignedUser(int personId, int taskId) {
        taskRepository.updateAssignedUser(personRepository.findByPersonId(personId), taskId);
        updateStatus(taskId, "PUBLISH");
    }

    public void deleteAssignAndRevertStatus(int taskId) {
        taskRepository.deleteAssignAndRevertStatus(taskId);
    }


    @Transactional
    public Task createNew(Person person) {

        Task add = new Task();

        add.setName("New task");
        add.setStatus("IN_DESIGN");
        add.setDescription("Description");
        add.setAuthor(person);
        add.setCreatedTime(new Date());
        add.setTaskSkills(Collections.EMPTY_LIST);
        add.setTaskReviews(Collections.EMPTY_LIST);
        add.setDeadline(new Date());
        return taskRepository.save(add);
    }

    @Transactional
    public void updateTask(TaskDTO task) {
        Task updating = taskRepository.findByTaskId(task.getId());
        updating.setName(task.getName());
        updating.setStatus(task.getStatus());
        updating.setDescription(task.getDescription());
        updating.setDeadline(task.getDeadline());
        updating.setAuthor(updating.getAuthor());
    }

    @Override
    public TaskDTO loadTask(int id) {
        return mapper.map(taskRepository.findByTaskId(id), TaskDTO.class);
    }


    @Override
    public Pager<TaskDTO> findAll(int id,
                                  int pageSize,
                                  int pageNumber,
                                  String pageSort,
                                  String pageName,
                                  String findName,
                                  Date date_from,
                                  Date date_to,
                                  Date due_from,
                                  Date due_to) {
        System.out.println("\n\n " + pageSort + "\n\n");
        PageAndSort pageAndSort = new PageAndSort(id, pageSort, pageNumber, pageSize, findName);
        Page<Task> page;
        switch (pageName) {
            case "candidate":
                page = taskRepository.findAllByCandidate(id,
                        PageRequest.of(pageNumber, pageSize, Sort.by(pageSort)));
                break;
            case "author":
                page = taskRepository.findAllByAuthor(id, PageRequest.of(pageNumber, pageSize, Sort.by(pageSort)));
                break;
            case "in_work":
                page = taskRepository.findAllInWork(id, "IN_WORK",
                        PageRequest.of(pageNumber, pageSize, Sort.by(pageSort)));
                break;
            default:
                page = taskRepository.find("IN_WORK", findName, date_from, date_to, due_from, due_to,
                        PageRequest.of(pageNumber, pageSize, Sort.by(pageSort)));
                break;
        }

        boolean hasPreviousPage = pageNumber != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageNumber;

        List<Task> list = page.getContent();
        List<TaskDTO> listDTO = list.stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
        return new Pager<>(listDTO, hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

    private String selectNextTaskStatus(String status) {
        LinkedList<String> statuses = new LinkedList<>();
        statuses.add("IN_DESIGN");
        statuses.add("PUBLISH");
        statuses.add("ASSIGNED");
        statuses.add("IN_WORK");
        statuses.add("DONE");
        if (!status.equals("DONE")) return statuses.get(statuses.indexOf(status) + 1);
        return null;
    }
}

