package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.*;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
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


    public void deleteTask(int id) {
        taskRepository.delete(taskRepository.findByTaskId(id));
    }

    public String updateStatus(int id, String status) {
        String nextStatus = selectNextTaskStatus(status);
        taskRepository.updateStatus(id,nextStatus);
        return nextStatus;
    }

    @Transactional
    public void updateAssignedUser(int personId, int taskId){
        taskRepository.updateAssignedUser(personRepository.findByPersonId(personId), taskId);
       /* System.out.println("!!!!!!!!!!!!!!!!!!!!!!!" + taskRepository.findByTaskId(taskId).getStatus());
        updateStatus(taskId, taskRepository.findByTaskId(taskId).getStatus());*/
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
    }

    @Override
    public TaskDTO loadTask(int id) {
        return mapper.map(taskRepository.findByTaskId(id), TaskDTO.class);
    }


    @Override
    public Pager findSorted(Optional<Integer> pageSize, Optional<Integer> pageNumber) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        PageAndSort pageAndSort = new PageAndSort("taskId", pageId, size, "");
        return findSort(pageAndSort);
    }

    public Pager findSorted(PageAndSort pageAndSort) {
        return findSort(pageAndSort);
    }

    private Pager findSort(PageAndSort pageAndSort) {
        int pageId = (pageAndSort.getFind().length() > 0) ? pageAndSort.getCurrentPage() : 0;
        int size = pageAndSort.getPageSize();
        String sortParam = pageAndSort.getSort();
        String find = pageAndSort.getFind();

        System.out.println("======================================" + PageRequest.of(pageId, size, Sort.by(sortParam)));
        Page<Task> page = taskRepository.findAll(PageRequest.of(pageId, size, Sort.by(sortParam)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        return new Pager(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByAuthor(int author_id) {
        return taskRepository.findAllByAuthor_PersonId(author_id).stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<TaskDTO> findAllByCandidate(int candidate_id) {
//        return taskRepository.findAllByCandidateId(candidate_id).stream()
//                .map(entity -> mapper.map(entity, TaskDTO.class))
//                .collect(Collectors.toList());
//    }

    private String selectNextTaskStatus(String status){
        LinkedList<String> statuses = new LinkedList<>();
        statuses.add("IN_DESIGN");
        statuses.add("PUBLISH");
        statuses.add("ASSIGNED");
        statuses.add("IN_WORK");
        statuses.add("DONE");
        if(!status.equals("DONE")) return statuses.get(statuses.indexOf(status)+1);
        return null;
    }
}


