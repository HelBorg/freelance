package com.freelance.project.demo.repository.specifications;

import com.freelance.project.demo.controller.SkillController;
import com.freelance.project.demo.models.Filter;
import com.freelance.project.demo.models.SkillFilter;
import com.freelance.project.demo.models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskSpecificationsBuilder {
    private List<SearchCriteria> params;
    private List<SearchCriteria> paramsSkills;

    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    public TaskSpecificationsBuilder(Filter filter) {
        logger.info("filter : {}", filter);
        params = new ArrayList<>();
        paramsSkills = new ArrayList<>();
        params.add(new SearchCriteria("name", ":", filter.getFindName()));
        params.add(new SearchCriteria("createdTime", ">=", filter.getDateFrom()));
        params.add(new SearchCriteria("createdTime", "<=", filter.getDateTo()));
        params.add(new SearchCriteria("deadline", ">=", filter.getDueFrom()));
        params.add(new SearchCriteria("deadline", "<=", filter.getDueTo()));
        if (!filter.getAuthor().equals(-1)) {
            params.add(new SearchCriteria("author", ":", filter.getAuthor()));
        }
        //params.add(new SearchCriteria("status", ":", "PUBLISHED"));
        if (filter.getSkills().size() != 0) {
            for(SkillFilter skillFilter : filter.getSkills()) {
                paramsSkills.add(new SearchCriteria("skills", skillFilter.getId(), "<=", skillFilter.getValue()));
            }
        }
    }

    //Building "or" and "and" separately, "or" for skills
    public Specification<Task> buildPartly(Boolean isOrPredicate) {
        int size = isOrPredicate ? paramsSkills.size() : params.size();
        if (size == 0) {
            return null;
        }
        List<Specification> specs = isOrPredicate ? paramsSkills.stream()
                .map(TaskSpecification::new).collect(Collectors.toList())
                : params.stream().map(TaskSpecification::new).collect(Collectors.toList());

        Specification result = specs.get(0);
        for (int i = 1; i < size; i++) {
            result = isOrPredicate ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }

    public Specification<Task> build() {
        logger.info(" build {}", params);
        logger.info(" build: {}", paramsSkills);
        Specification<Task> spec = this.buildPartly(false);
        if (paramsSkills.size() != 0) {
            logger.info("params.size() != 0");
            spec = Specification.where(this.buildPartly(false)).and(this.buildPartly(true));
        }
        return spec;
    }

}
