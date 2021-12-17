package com.example.proj.repository;

import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends Neo4jRepository<TaskEntity, Long> {
    @Query("MATCH (t:Task) WHERE t.status = \"NOT_ACTIVE\" AND t.project = $project RETURN collect(t)")
    List<TaskEntity> customQueryGetNotActiveTasksFromProject(@Param("project") String projectName);

    @Query("MATCH (t: Task{project: $projectName}) DETACH DELETE t")
    void customQueryDeleteAllTasksFromProject(@Param("projectName") String project);

}
