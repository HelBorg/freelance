package com.freelance.project.demo.controller;


import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.dto.ReviewDTO;
import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Review;
import com.freelance.project.demo.service.ReviewService;
import org.dozer.DozerBeanMapper;
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

    @PostMapping
    public void create(@RequestBody Review review, @AuthenticationPrincipal UserDetails userDetails){
        reviewService.createReview(review,userDetails);
    }

    @GetMapping("/{id}")
    public List<ReviewDTO> getAllByTask(@PathVariable int id){
        return reviewService.getAllByTask(id);
    }
}
