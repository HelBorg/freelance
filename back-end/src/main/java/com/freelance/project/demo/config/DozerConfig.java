package com.freelance.project.demo.config;

import com.freelance.project.demo.dto.*;
import com.freelance.project.demo.models.*;
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
                        .fields(field("personId").accessible(), field("id").accessible())
                        .fields(field("name").accessible(), field("name").accessible())
                        .fields(field("rating").accessible(), field("rating").accessible())
                        .fields(field("userSkills").accessible(), field("skills").accessible())
                        .fields(field("tasksDone").accessible(), field("tasksDone").accessible())
                        .fields(field("email").accessible(), field("email").accessible());
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
                        .fields(field("taskSkills").accessible(), field("skills").accessible())
                        .fields(field("taskReviews").accessible(), field("reviews").accessible())
                        .fields(field("author").accessible(), field("author").accessible())
                        .fields(field("assignedUser").accessible(), field("assignedUser").accessible());
            }
        };
    }

    @Bean
    public BeanMappingBuilder reviewMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Review.class, ReviewDTO.class)
                        .fields(field("reviewId").accessible(), field("id").accessible())
                        .fields(field("description").accessible(), field("description").accessible())
                        .fields(field("dateTime").accessible(), field("createdTime").accessible())
                        .fields(field("done").accessible(), field("done").accessible())
                        .fields(field("parentId").accessible(), field("parentId").accessible())
                        .fields(field("userId").accessible(), field("user").accessible());


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
                        .fields(field("skillId").accessible(), field("skillName").accessible());

            }
        };

    }

    @Bean
    public BeanMappingBuilder personSkillMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(UserSkill.class, UserSkillDTO.class)
                        .fields(field("userSkillId").accessible(), field("id").accessible())
                        .fields(field("level").accessible(), field("level").accessible())
                        .fields(field("skillId").accessible(), field("skillName").accessible());

            }
        };

    }

    @Bean
    public BeanMappingBuilder skillMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Skill.class, SkillDTO.class)
                        .fields(field("name").accessible(), field("name").accessible());

            }
        };

    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.addMapping(personMappingBuilder());
        dozerBeanMapper.addMapping(taskMappingBuilder());
        dozerBeanMapper.addMapping(taskSkillMappingBuilder());
        dozerBeanMapper.addMapping(reviewMappingBuilder());
        dozerBeanMapper.addMapping(skillMappingBuilder());
        dozerBeanMapper.addMapping(personSkillMappingBuilder());

        return dozerBeanMapper;
    }
}
