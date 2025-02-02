package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.repository.specifications.SearchCriteria;
import com.freelance.project.demo.repository.specifications.TaskSpecification;
import com.freelance.project.demo.repository.specifications.TaskSpecificationsBuilder;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
        updateStatus(taskId, "PUBLISHED");
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

        taskRepository.save(updating);
    }

    @Override
    public TaskDTO loadTask(int id) {
        return mapper.map(taskRepository.findByTaskId(id), TaskDTO.class);
    }


    @Override
    public Page<Task> findAll(PageRequest request, Filter filter) {
        TaskSpecification taskSpecification = new TaskSpecification(
                new SearchCriteria("status", ":", "PUBLISHED"));
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(filter);
        Specification<Task> spec = Specification.where(builder.build()).and(taskSpecification);
        return taskRepository.findAll(spec, request);
    }

    @Override
    public Page<Task> getByCandidateId(PageRequest request, Filter filter) {
        TaskSpecification taskSpecification = new TaskSpecification(
                new SearchCriteria("candidateId", ":", filter.getId()));
        TaskSpecification taskSpecification2 = new TaskSpecification(
                new SearchCriteria("status", ":", "PUBLISHED"));
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(filter);
        Specification<Task> spec = Specification.where(builder.build()).and(taskSpecification).and(taskSpecification2);
        return taskRepository.findAll(spec, request);
    }

    @Override
    public Page<Task> getByAuthorId(PageRequest request, Filter filter) {
        TaskSpecification taskSpecification = new TaskSpecification(
                new SearchCriteria("author", ":", filter.getId()));
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(filter);
        Specification<Task> spec = Specification.where(builder.build()).and(taskSpecification);
        return taskRepository.findAll(spec, request);
    }

    @Override
    public Page<Task> getByAssignedUserId(PageRequest request, Filter filter) {
        TaskSpecification taskSpecification = new TaskSpecification(
                new SearchCriteria("assignedUserId", ":", filter.getId()));
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder(filter);
        Specification<Task> spec = Specification.where(builder.build()).and(taskSpecification);
        return taskRepository.findAll(spec, request);
    }


    private String selectNextTaskStatus(String status) {
        LinkedList<String> statuses = new LinkedList<>();
        statuses.add("IN_DESIGN");
        statuses.add("PUBLISHED");
        statuses.add("ASSIGNED");
        statuses.add("IN_WORK");
        statuses.add("DONE");
        if (!status.equals("DONE")) return statuses.get(statuses.indexOf(status) + 1);
        return null;
    }
}

