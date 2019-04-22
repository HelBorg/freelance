package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.service.impl.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    @Autowired
    private SkillServiceImpl service;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

}
