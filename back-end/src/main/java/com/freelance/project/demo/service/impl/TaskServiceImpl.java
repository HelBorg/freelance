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
    public Pager<Task> findAll(Optional<Integer> pageSize,
                            Optional<Integer> pageNumber,
                            Optional<String> pageSort) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = pageSort.orElse("taskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        Page<Task> page = taskRepository.find(PageRequest.of(pageId, size, Sort.by(sort)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;
        return new Pager<>(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }


    @Override
    public Pager<Task> findAllByAuthor(int author_id,
                                 Optional<Integer> pageSize,
                                 Optional<Integer> pageNumber,
                                 Optional<String> sortAll) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = sortAll.orElse("taskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        Page<Task> page = taskRepository.findAllByAuthor(author_id, PageRequest.of(pageId, size, Sort.by(sort)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        return new Pager<>(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }


    @Override
    public Pager<Task> findAllByCandidate(int candidate_id,
                                    Optional<Integer> pageSize,
                                    Optional<Integer> pageNumber,
                                    Optional<String> sortCand) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = sortCand.orElse("taskId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        Page<Task> page = taskRepository.findAllByCandidate(candidate_id, PageRequest.of(pageId, size, Sort.by(sort)));

        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        return new Pager<>(page.getContent(), hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

}

