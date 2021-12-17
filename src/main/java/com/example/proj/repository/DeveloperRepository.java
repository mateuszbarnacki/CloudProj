package com.example.proj.repository;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.Employee;
import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends Neo4jRepository<DeveloperEntity, Long> {
    Optional<DeveloperEntity> findDeveloperEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH (d: Developer) WHERE NOT (d)<-[:GIVE_TASKS_FOR]-(:TechLeader) RETURN collect(d)")
    List<DeveloperEntity> customQueryGetAvailableDevelopers();

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

    @Query("MATCH (t:Task {status:\"NOT_ACTIVE\"}) RETURN collect(t)")
    List<TaskEntity> customQueryGetAllNotActiveTasks();

    @Query("MATCH (d: Developer {name: $developer.name, surname: $developer.surname, email: $developer.email})<-" +
            "[:GIVE_TASKS_FOR]-(t: TechLeader)-" +
            "[:GIVE_TASKS_FOR]->(od: Developer) " +
            "MATCH (d)<-[:GIVE_TASKS_FOR]-(:TechLeader)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
            "RETURN collect(t), collect(od), collect(po)")
    List<Employee> customQueryGetTeammates(@Param("developer") DeveloperEntity developer);

    @Query("MATCH (t:Task {status:\"ACTIVE\"})-" +
            "[:SOLVED_BY]->(:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGetCurrentTasks(@Param("developer") DeveloperEntity developer);

    @Query("MATCH (t:Task {status:\"FINISHED\"})-[:SOLVED_BY]->(:Developer {name: $developer.name, surname: $developer.surname, email: $developer.email}) \n" +
            "WITH collect(t.project) AS projects\n" +
            "MATCH (nt:Task {status:\"NOT_ACTIVE\"})\n" +
            "WHERE any(suggestion IN projects WHERE suggestion = nt.project)\n" +
            "RETURN collect(nt) \n" +
            "LIMIT 5")
    List<TaskEntity> customQueryGetSuggestedTasks(@Param("developer") DeveloperEntity developer);

}
