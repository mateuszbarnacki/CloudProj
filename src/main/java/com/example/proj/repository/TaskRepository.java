package com.example.proj.repository;

import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface TaskRepository extends Neo4jRepository<TaskEntity, Long> {
    @Query("")
    List<TaskEntity> customQueryGetTasksByEmployeeEmail(String email);

    @Query("")
    List<TaskEntity> customQueryGetTasksCreatedByEmployee(String email);

    @Query("")
    List<TaskEntity> customQueryGetNotActiveTasksFromProject(String projectName);

    @Query("")
    List<TaskEntity> customQueryGetAllNotActiveTasks();

}
