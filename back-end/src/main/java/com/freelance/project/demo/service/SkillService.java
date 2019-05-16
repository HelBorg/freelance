package com.freelance.project.demo.service;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.models.Skill;

import java.util.List;

public interface SkillService  {

    List<SkillDTO> findAll();

    Skill findByName(String name);
}
