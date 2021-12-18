package com.example.proj.repository;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TaskEntity;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends Neo4jRepository<TaskEntity, Long> {
    @Query("MATCH (tl:TechLeader {name: $techLeader.name, surname: $techLeader.surname, email: $techLeader.email}) " +
            "CREATE (t:Task {title: $task.title project: $task.project, description: $task.description, status: $task.status})-" +
            "[r:CREATED_BY]->(tl) " +
            "RETURN t")
    Optional<TaskEntity> customQueryCreateTask(@Param("techLeader") TechLeaderEntity techLeaderEntity,
                                             @Param("task") TaskEntity taskEntity);

    @Query("MATCH (t:Task {title: $task.title, project: $task.project, description: $task.description, status: $task.status}), " +
            "(d:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) " +
            "CREATE (t)-[:SOLVED_BY]->(d) " +
            "SET t.status = \"ACTIVE\" " +
            "RETURN t")
    Optional<TaskEntity> customQueryStartTask(@Param("task") TaskEntity taskEntity,
                                              @Param("developer") DeveloperEntity developerEntity);

    @Query("MATCH " +
            "(t:Task {title: $task.title, project: $task.project, description: $task.description, status: $task.status})-" +
            "[:SOLVED_BY]->(:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) " +
            "SET t.status = \"FINISHED\" " +
            "RETURN t")
    Optional<TaskEntity> customQueryFinishTask(@Param("task") TaskEntity taskEntity,
                                               @Param("developer") DeveloperEntity developerEntity);

    @Query("MATCH (t:Task) WHERE t.status = \"NOT_ACTIVE\" RETURN collect(t)")
    List<TaskEntity> customQueryGetAllNotActiveTasks();

    @Query("MATCH (t:Task {status:\"ACTIVE\"})-" +
            "[:SOLVED_BY]->(:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGetCurrentTasks(@Param("developer") DeveloperEntity developer);

    @Query("MATCH (t:Task) WHERE t.status = \"NOT_ACTIVE\" AND t.project = $project RETURN collect(t)")
    List<TaskEntity> customQueryGetNotActiveTasksFromProject(@Param("project") String projectName);

    @Query("MATCH (t:Task {status:\"FINISHED\"})-[:SOLVED_BY]->(:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) \n" +
            "WITH collect(t.project) AS projects\n" +
            "MATCH (nt:Task {status:\"NOT_ACTIVE\"})\n" +
            "WHERE any(suggestion IN projects WHERE suggestion = nt.project)\n" +
            "RETURN collect(nt) \n" +
            "LIMIT 5")
    List<TaskEntity> customQueryGetSuggestedTasks(@Param("developer") DeveloperEntity developer);

    @Query("MATCH (:ProductOwner {name: $productOwner.name, surname: $productOwner.surname, email: $productOwner.email})-" +
            "[:COOPERATES_WITH]->(:TechLeader)<-[:CREATED_BY]-(t:Task) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGenerateReport(@Param("productOwner") ProductOwnerEntity productOwnerEntity);

    @Query("MATCH (t: Task{project: $projectName}) DETACH DELETE t")
    void customQueryDeleteAllTasksFromProject(@Param("projectName") String project);

}
