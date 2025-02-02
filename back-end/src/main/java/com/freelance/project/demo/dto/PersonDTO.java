package com.freelance.project.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class PersonDTO implements Serializable {

    int id;
    String name;
    int rating;
    String email;
    List<UserSkillDTO> skills;
    int placeInRating;
    int tasksDone;
}
