package com.freelance.project.demo.repository.specifications;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

public class TaskSpecification implements Specification<Task> {

    private SearchCriteria criteria;

    public TaskSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getKey().equals("skills")) {
            Join<Task, TaskSkill> taskSkillJoin = root.join("taskSkills");
            Predicate skillValue = builder.equal(taskSkillJoin.get("level"), criteria.getValue());
            Predicate skillId = builder.equal(taskSkillJoin.get("skillId"), criteria.getId());
            return builder.and(skillId, skillValue);
        } else if (criteria.getKey().equals("author")) {
            Join<Task, Person> taskAuthorJoin = root.join("author");
            return builder.equal(taskAuthorJoin.get("personId"), criteria.getValue());
        } else if (criteria.getKey().equals("createdTime") || (criteria.getKey().equals("deadline"))) {
            if (criteria.getOperation().equalsIgnoreCase(">=")) {
                return builder.greaterThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
                return builder.lessThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
            }
        }
        return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
    }
}

