package com.freelance.project.demo.config;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Tasks;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {
    @Bean
    public BeanMappingBuilder personMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Person.class, PersonDTO.class)
                        .fields(field("personId").accessible(), field("id").accessible());

            }
        };
    }

    @Bean
    public BeanMappingBuilder taskMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Tasks.class, TaskDTO.class)
                        .fields(field("taskId").accessible(), field("id").accessible())
                        .fields(field("name").accessible(), field("name").accessible())
                        .fields(field("status").accessible(), field("status").accessible())
                        .fields(field("status").accessible(), field("status").accessible());



            }
        };
    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.addMapping(personMappingBuilder());
        dozerBeanMapper.addMapping(taskMappingBuilder());
        return dozerBeanMapper;
    }
}
