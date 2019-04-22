package com.freelance.project.demo.config;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;
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
                mapping(Task.class, TaskDTO.class)
                        .fields(field("taskId").accessible(), field("id").accessible())
                        .fields(field("name").accessible(), field("name").accessible())
                        .fields(field("status").accessible(), field("status").accessible())
                        .fields(field("description").accessible(), field("description").accessible())
                        .fields(field("createdTime").accessible(), field("createdTime").accessible())
                        .fields(field("deadline").accessible(), field("deadline").accessible())
                        .fields(field("taskSkills").accessible(), field("skills").accessible());

            }
        };
    }
    @Bean
    public BeanMappingBuilder taskSkillMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(TaskSkill.class, TaskSkillDTO.class)
                        .fields(field("taskSkillId").accessible(), field("id").accessible())
                        .fields(field("level").accessible(), field("level").accessible())
                        .fields(field("taskId").accessible(), field("taskId").accessible())
                        .fields(field("skillId").accessible(), field("skillName").accessible());


            }
        };

    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.addMapping(personMappingBuilder());
        dozerBeanMapper.addMapping(taskMappingBuilder());
        dozerBeanMapper.addMapping(taskSkillMappingBuilder());

        return dozerBeanMapper;
    }
}
