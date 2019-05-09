package com.freelance.project.demo.dto;


import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {

     int id;
     boolean done;
     String description;
     Date createdTime;
     PersonDTO user;

}
