package com.freelance.project.demo.dto;


import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {

     int id;
     String description;
     Date createdTime;
     PersonDTO user;
}
