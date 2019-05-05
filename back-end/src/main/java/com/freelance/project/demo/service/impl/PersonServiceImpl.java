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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public Pager<PersonDTO> findAll(Optional<Integer> pageSize,
                                   Optional<Integer> pageNumber,
                                   Optional<String> pageSort) {
        int pageId = pageNumber.orElse(0);
        int size = pageSize.orElse(5);
        String sort = pageSort.orElse("personId");
        PageAndSort pageAndSort = new PageAndSort(sort, pageId, size, "");
        Page<Person> page = repository.find(PageRequest.of(pageId, size, Sort.by(sort)));


        boolean hasPreviousPage = pageId != 0;
        boolean hasNextPage = page.getTotalPages() - 1 > pageId;

        List<Person> list = page.getContent();
        List<PersonDTO> listDTO = list.stream()
                .map(entity -> mapper.map(entity, PersonDTO.class))
                .collect(Collectors.toList());
        return new Pager<>(listDTO, hasPreviousPage, hasNextPage, page.getTotalPages(), pageAndSort);

    }

    public Person findByEmail(String email) {
        return repository.findByEmail(email);
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
        repository.save(add);
    }
}
