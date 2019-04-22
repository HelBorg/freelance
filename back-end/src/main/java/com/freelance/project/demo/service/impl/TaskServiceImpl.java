package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Task;
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
    private DozerBeanMapper mapper;

    @Transactional
    public Task createNew(Person person) {
        Task add = new Task();
        add.setName("New task");
        add.setStatus("IN_DESIGN");
        add.setAuthor(person);
        return taskRepository.save(add);
    }

    @Override
    public TaskDTO loadTask(int id) {
        return mapper.map(taskRepository.findByTaskId(id), TaskDTO.class);
    }

    @Transactional
    public void updateTask(TaskDTO task) {
        Task updating = taskRepository.findByTaskId(task.getId());
        updating.setName(task.getName());
        updating.setStatus(task.getStatus());
        updating.setDescription(task.getDescription());
    }

    @Override
    public Pager findAll(Optional<Integer> pageSize,
                            Optional<Integer> pageNumber,
                            Optional<String> pageSort) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = pageSort.orElse("taskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");

        Page<Task> page = taskRepository.findAll(PageRequest.of(pageId, size, Sort.by(sort)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;
        return new Pager(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

//    private Pager findSort(PageAndSort pageAndSort) {
//        System.out.println(pageAndSort.getFind().length());
//        System.out.println(pageAndSort.getCurrentPage());
//        int pageId = (pageAndSort.getFind().length() > 0) ? 0 : pageAndSort.getCurrentPage();
//        int size = pageAndSort.getPageSize();
//        String sortParam = pageAndSort.getSort();
//        String find = pageAndSort.getFind();
//
//        Page<Task> page = taskRepository.findAll(PageRequest.of(pageId, size, Sort.by(sortParam)));
//
//        boolean hasPreviousPage = pageId != 0;
//        boolean hasNextPage = page.getTotalPages() - 1 > pageId;
//
//        return new Pager(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
//    }

//    @Override
//    public List<TaskDTO> findAll() {
//        return taskRepository.findAll().stream()
//                .map(entity -> mapper.map(entity, TaskDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public Pager findAllByAuthor(int author_id,
                                 Optional<Integer> pageSize,
                                 Optional<Integer> pageNumber,
                                 Optional<String> sortAll) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = sortAll.orElse("TaskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        return findSortAuthor(author_id, pageAndSort);
    }

    private Pager findSortAuthor(int author_id, PageAndSort pageAndSort) {
        int pageId = (pageAndSort.getFind().length() > 0) ? 0 : pageAndSort.getCurrentPage();
        int size = pageAndSort.getPageSize();
        String sortParam = pageAndSort.getSort();
        String find = pageAndSort.getFind();

        Page<Task> page = taskRepository.findAllByAuthor_PersonId(author_id, PageRequest.of(pageId, size, Sort.by(sortParam)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        return new Pager(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }



    @Override
    public Pager findAllByCandidate(int candidate_id,
                                    Optional<Integer> pageSize,
                                    Optional<Integer> pageNumber,
                                    Optional<String> sortCand) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = sortCand.orElse("TaskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        return findSortCandidates(candidate_id, pageAndSort);
    }

    private Pager findSortCandidates(int candidate_id, PageAndSort pageAndSort) {
        int pageId = (pageAndSort.getFind().length() > 0) ? 0 : pageAndSort.getCurrentPage();
        int size = pageAndSort.getPageSize();
        String sortParam = pageAndSort.getSort();
        String find = pageAndSort.getFind();

        Page<Task> page = taskRepository.findAllByCandidate(candidate_id, PageRequest.of(pageId, size, Sort.by(sortParam)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        return new Pager(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

}

