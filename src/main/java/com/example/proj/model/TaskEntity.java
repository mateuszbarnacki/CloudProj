package com.example.proj.model;

import com.example.proj.common.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Task")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Setter
public class TaskEntity {
    private @Id @GeneratedValue Long id;
    private String description;
    private String project;
    private Status status;
    private String title;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Employee> creators = new HashSet<>();
    @Relationship(type = "SOLVED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Employee> solvers = new HashSet<>();

    public TaskEntity(String title, String content, String projectName, Status status) {
        this.title = title;
        this.description = content;
        this.project = projectName;
        this.status = status;
    }

}
