package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Skills;
import com.freelance.project.demo.repository.SkillsRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillsServiceImpl {

    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private DozerBeanMapper mapper;


    public List<Skills> findAllSkills() {
        return skillsRepository.findAll();
    }

}
