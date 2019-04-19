package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.dto.TaskDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public Pager findSorted(Optional<Integer> pageSize, Optional<Integer> pageNumber) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        PageAndSort pageAndSort = new PageAndSort("id", pageId, size, "");
        return findSort(pageAndSort);
    }

    @Override
    public Pager findSorted(PageAndSort pageAndSort) {
        return findSort(pageAndSort);
    }

    private Pager findSort(PageAndSort pageAndSort) {
        int pageId = (pageAndSort.getFind().length() > 0) ? pageAndSort.getCurrentPage() : 0;
        int size = pageAndSort.getPageSize();
        String sortParam = pageAndSort.getSort();
        String find = pageAndSort.getFind();

        Page<Task> page = (find.length() > 0) ? taskRepository.findByName(find, PageRequest.of(pageId, size, Sort.by(sortParam))) :
                taskRepository.findAll(PageRequest.of(pageId, size, Sort.by(sortParam)));

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
    public List<TaskDTO> findAllByAuthor(Long author_id) {
        return taskRepository.findAllByAuthor_PersonId(author_id).stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByCandidate(Long candidate_id) {
        return taskRepository.findAllByCandidateId(candidate_id).stream()
                .map(entity -> mapper.map(entity, TaskDTO.class))
                .collect(Collectors.toList());
    }

}
