package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.service.impl.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private SkillServiceImpl service;

    @GetMapping
    public Collection<SkillDTO> getAll() {
        return service.findAll();
    }
}
