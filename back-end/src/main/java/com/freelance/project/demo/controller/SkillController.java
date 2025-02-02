package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private SkillRepository repository;

    @Autowired
    private SkillService service;

    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    @GetMapping
    public Collection<SkillDTO> getAll() {
        logger.info("Request to get all skills");
        return service.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity getSkillByName(@PathVariable("name") String name) {
        logger.info("Request to get skill by name: {}", name);
        return ok(repository.findSkillByName(name).getSkillId());
    }

}
