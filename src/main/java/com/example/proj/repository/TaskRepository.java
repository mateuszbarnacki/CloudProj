package com.example.proj.repository;

import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends Neo4jRepository<TaskEntity, Long> {
    @Query("MATCH (tl:TechLeader {name: $techLeaderName, surname: $techLeaderSurname, email: $techLeaderEmail}) " +
            "CREATE (t:Task {title: $taskTitle, project: $taskProject, description: $taskDescription, status: $taskStatus})-" +
            "[r:CREATED_BY]->(tl) " +
            "RETURN t")
    Optional<TaskEntity> customQueryCreateTask(@Param("techLeaderName") String techLeaderName,
                                               @Param("techLeaderSurname") String techLeaderSurname,
                                               @Param("techLeaderEmail") String techLeaderEmail,
                                               @Param("taskTitle") String taskTitle,
                                               @Param("taskProject") String taskProject,
                                               @Param("taskDescription") String taskDescription,
                                               @Param("taskStatus") String taskStatus);

    @Query("MATCH (t:Task {title: $taskTitle, project: $taskProject, description: $taskDescription, status: $taskStatus}), " +
            "(d:Developer {name: $developerName, surname: $developerSurname, email: $developerEmail}) " +
            "CREATE (t)-[:SOLVED_BY]->(d) " +
            "SET t.status = \"ACTIVE\" " +
            "RETURN t")
    Optional<TaskEntity> customQueryStartTask(@Param("taskTitle") String taskTitle,
                                              @Param("taskProject") String taskProject,
                                              @Param("taskDescription") String taskDescription,
                                              @Param("taskStatus") String taskStatus,
                                              @Param("developerName") String developerName,
                                              @Param("developerSurname") String developerSurname,
                                              @Param("developerEmail") String developerEmail);

    @Query("MATCH " +
            "(t:Task {title: $taskTitle, project: $taskProject, description: $taskDescription, status: $taskStatus})-" +
            "[:SOLVED_BY]->(:Developer {name: $developerName, surname: $developerSurname, email: $developerEmail}) " +
            "SET t.status = \"FINISHED\" " +
            "RETURN t")
    Optional<TaskEntity> customQueryFinishTask(@Param("taskTitle") String taskTitle,
                                               @Param("taskProject") String taskProject,
                                               @Param("taskDescription") String taskDescription,
                                               @Param("taskStatus") String taskStatus,
                                               @Param("developerName") String developerName,
                                               @Param("developerSurname") String developerSurname,
                                               @Param("developerEmail") String developerEmail);

    @Query("MATCH (t:Task) WHERE t.status = \"NOT_ACTIVE\" RETURN collect(t)")
    List<TaskEntity> customQueryGetAllNotActiveTasks();

    @Query("MATCH (t:Task {status:\"ACTIVE\"})-" +
            "[:SOLVED_BY]->(:Developer {name: $developerName, surname: $developerSurname, email: $developerEmail}) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGetCurrentTasks(@Param("developerName") String developerName,
                                                @Param("developerSurname") String developerSurname,
                                                @Param("developerEmail") String developerEmail);

    @Query("MATCH (t:Task) WHERE t.status = \"NOT_ACTIVE\" AND t.project = $project RETURN collect(t)")
    List<TaskEntity> customQueryGetNotActiveTasksFromProject(@Param("project") String projectName);

    @Query("MATCH (t:Task)-[:SOLVED_BY]->(:Developer {name: $developerName, surname: $developerSurname, email: $developerEmail}) " +
            "WHERE t.status=\"ACTIVE\" OR t.status=\"FINISHED\" " +
            "WITH collect(t.project) AS projects " +
            "MATCH (nt:Task {status:\"NOT_ACTIVE\"}) " +
            "WHERE any(suggestion IN projects WHERE suggestion = nt.project) " +
            "RETURN collect(nt) " +
            "LIMIT 5")
    List<TaskEntity> customQueryGetSuggestedTasks(@Param("developerName") String developerName,
                                                  @Param("developerSurname") String developerSurname,
                                                  @Param("developerEmail") String developerEmail);

    @Query("MATCH (:ProductOwner {name: $productOwnerName, surname: $productOwnerSurname, email: $productOwnerEmail})-" +
            "[:COOPERATES_WITH]->(:TechLeader)<-[:CREATED_BY]-(t:Task) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGenerateReport(@Param("productOwnerName") String developerName,
                                               @Param("productOwnerSurname") String developerSurname,
                                               @Param("productOwnerEmail") String developerEmail);

    @Query("MATCH (t: Task{project: $projectName}) DETACH DELETE t")
    void customQueryDeleteAllTasksFromProject(@Param("projectName") String project);

}
