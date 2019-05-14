package com.freelance.project.demo.controller;


import com.freelance.project.demo.dto.ReviewDTO;
import com.freelance.project.demo.models.Review;
import com.freelance.project.demo.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping
    public void create(@RequestBody Review review, @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Request to create new review: review - {}, user details - {}", review, userDetails);
        reviewService.createReview(review, userDetails);
    }

    @GetMapping("/{id}")
    public List<ReviewDTO> getAllByTask(@PathVariable int id) {
        logger.info("Request to get all reviews by task id: {}", id);
        return reviewService.getAllByTask(id);
    }

    @GetMapping("/user/{id}")
    public List<ReviewDTO> getAllAboutUser(@PathVariable int id) {
        return reviewService.getAllForUser(id);
    }

    @GetMapping("/subcomments/{id}")
    public List<ReviewDTO> getAllSubComments(@PathVariable int id) {
        return reviewService.findAllSubComments(id);
    }
}
