package com.freelance.project.demo.repository.specifications;

import com.freelance.project.demo.models.Person;
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
            System.out.println("Skills specification" + criteria.getValue() + " " + criteria.getField() + "\n");
            Join<Task, TaskSkill> taskSkillJoin = root.join("taskId");
            return builder.equal(taskSkillJoin.get(criteria.getField()), criteria.getValue());

        } else if (criteria.getKey().equals("author")) {
            Join<Task, Person> taskAuthorJoin = root.join("author");
            return builder.equal(taskAuthorJoin.get("name"), criteria.getValue());

        } else if (criteria.getKey().equals("author_id")) {
            Join<Task, Person> taskAuthorJoin = root.join("author");
            return builder.equal(taskAuthorJoin.get("personId"), criteria.getValue());

        } else if (criteria.getKey().equals("candidate_id")) {
            Join<Task, Person> taskAuthorJoin = root.join("candidateTasks");
            return builder.equal(taskAuthorJoin.get("personId"), criteria.getValue());
        } else if (criteria.getKey().equals("createdTime")||(criteria.getKey().equals("deadline"))) {
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

