package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.controller.AuthenticationRequest;
import com.freelance.project.demo.dto.ReviewDTO;
import com.freelance.project.demo.models.Review;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.repository.ReviewRepository;
import com.freelance.project.demo.repository.TaskRepository;
import com.freelance.project.demo.service.ReviewService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DozerBeanMapper mapper;

    @Transactional
    public void createReview(Review review, UserDetails userDetails){

        review.setDescription(review.getDescription());
        review.setDateTime(new Date());
        review.setTaskId(taskRepository.findByTaskId(review.getTaskId().getTaskId()));
        review.setUserId(personRepository.findByEmail(userDetails.getUsername()));
        reviewRepository.save(review);
        //Many to many candidate tasks
        taskRepository.findByTaskId(review.getTaskId().getTaskId())
                .getCandidateTasks().add(personRepository.findByEmail(userDetails.getUsername()));
        personRepository.findByEmail(userDetails.getUsername())
                .getCandidateTasks().add(taskRepository.findByTaskId(review.getTaskId().getTaskId()));
    }

    public List<ReviewDTO> getAllByTask(int taskId){
        return reviewRepository.findAllForTask(taskId).stream()
                .map(entity -> mapper.map(entity, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}
