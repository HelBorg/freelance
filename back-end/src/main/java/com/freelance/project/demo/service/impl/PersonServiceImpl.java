package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.PersonService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public List<PersonDTO> findAll() {
        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, PersonDTO.class))
                .collect(Collectors.toList());
    }

    public Person findByEmail(String email){
        return repository.findPersonByEmail(email);
    }
}
