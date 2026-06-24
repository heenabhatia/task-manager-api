package com.heena.taskmanager.repository.specification;

import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> hasStatus(Status status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(Priority priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasCategory(Category category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }
}
