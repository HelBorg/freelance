package com.freelance.project.demo.service;


import com.freelance.project.demo.controller.AuthenticationRequest;
import com.freelance.project.demo.dto.ReviewDTO;
import com.freelance.project.demo.models.Review;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ReviewService {

    void createReview(Review review, UserDetails userDetails);

    List<ReviewDTO> getAllByTask(int taskId);

    List<ReviewDTO> getAllForUser(int userId);


}
