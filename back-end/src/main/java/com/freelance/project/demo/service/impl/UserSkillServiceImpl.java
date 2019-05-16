package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.ReviewDTO;
import com.freelance.project.demo.dto.UserSkillDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.UserSkill;
import com.freelance.project.demo.repository.*;
import com.freelance.project.demo.service.UserSkillService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserSkillRepository userSkillRepository;

    @Autowired
    private DozerBeanMapper mapper;

    public List<UserSkillDTO> findAllForPerson (int personId){
        return userSkillRepository.findAllByPersonId(personRepository.findByPersonId(personId)).stream()
                .map(entity -> mapper.map(entity, UserSkillDTO.class))
                .collect(Collectors.toList());
    };

    public void deleteTaskSkill(int id){
        userSkillRepository.delete(userSkillRepository.findByUserSkillId(id));
    }

    public void addNewUserSkill(UserSkillDTO userSkillDTO, int personId) {

       UserSkill newUserSkill = new UserSkill();
        newUserSkill.setPersonId(personRepository.findByPersonId(personId));
        newUserSkill.setLevel(userSkillDTO.getLevel());
        newUserSkill.setSkillId(skillRepository.findSkillByName(userSkillDTO.getSkillName().getName()));

        userSkillRepository.save(newUserSkill);

    }
}
