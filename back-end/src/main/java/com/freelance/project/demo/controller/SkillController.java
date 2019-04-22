package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.service.impl.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private SkillRepository repository;

    @GetMapping
    public Collection<Skill> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity getSkillByName(@PathVariable("name") String name){
        return ok(repository.findSkillByName(name).getSkillId());
    }

}
