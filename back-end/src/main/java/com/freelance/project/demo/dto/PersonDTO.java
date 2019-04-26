package com.freelance.project.demo.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class PersonDTO implements Serializable {
    private int id;
    private String name;
}
