package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    List<PersonDTO> findAll();
}
