package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.repository.specifications.TaskSpecificationsBuilder;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
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
    public Page<Task> getByCandidateId(PageRequest request, Integer id) {
        return taskRepository.findAllByCandidateId(id, request);
    }

    @Override
    public Page<Task> getByAuthorId(PageRequest request, Integer id) {
        return taskRepository.findAllByAuthor(id, request);
    }

    @Override
    public Page<Task> getByAssignedUserId(PageRequest request, Integer id) {
        return taskRepository.findAllByAssignedUser(id, request);
    }

    @Override
    public Page<Task> findAll(Filter filter, PageRequest request) {
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(filter);
        Specification<Task> spec = builder.build();
        return taskRepository.findAll(spec, request);
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

