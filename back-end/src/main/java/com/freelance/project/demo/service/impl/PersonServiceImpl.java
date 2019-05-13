package com.freelance.project.demo.service.impl;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.PageAndSort;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.PersonService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DozerBeanMapper mapper;

    public void updateRating(int id, int rate){
        Person updated =  personRepository.findByPersonId(id);
           updated.setRating(updated.getRating() + rate);
           personRepository.save(updated);
    }

    @Override
    public Pager<PersonDTO> findAll(PageAndSort pageAndSort) {
        Page<Person> page = personRepository.findByName(pageAndSort.getFindName(), PageRequest.of(pageAndSort.getCurrentPage(),
                pageAndSort.getPageSize(), pageAndSort.getSort()));

        boolean hasPreviousPage = pageAndSort.getCurrentPage() != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageAndSort.getCurrentPage();
        List<PersonDTO> listDTO = page.getContent().stream()
                .map(entity -> mapper.map(entity, PersonDTO.class))
                .collect(Collectors.toList());
        return new Pager<>(listDTO, hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);
    }

    public Person getById(int id){
        return personRepository.findByPersonId(id);
    }
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public void create(Person person) {

        if (findByEmail(person.getEmail()) != null) {
            System.out.println("User already exists");
        }
        Person add = new Person();
        add.setName(person.getName());
        add.setEmail(person.getEmail());
        add.setPassword(person.getPassword());
        add.setRating(0);
        add.setRole("user");
        add.setUserSkills(Collections.EMPTY_LIST);
        personRepository.save(add);
    }
}
