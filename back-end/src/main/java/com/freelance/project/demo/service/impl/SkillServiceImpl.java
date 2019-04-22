package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.service.SkillService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository;


    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public List<SkillDTO> findAll() {
        return skillRepository.findAll().stream()
                .map(entity -> mapper.map(entity, SkillDTO.class))
                .collect(Collectors.toList());
    }


    public Skill findByName(String name){
        return skillRepository.findSkillByName(name);
    }
}
