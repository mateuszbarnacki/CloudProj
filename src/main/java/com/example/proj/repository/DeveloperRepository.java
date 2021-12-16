package com.example.proj.repository;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.Employee;
import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeveloperRepository extends Neo4jRepository<DeveloperEntity, Long> {
    DeveloperEntity findDeveloperEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH (d: Developer) WHERE NOT (d)<-[:GIVE_TASKS_FOR]-(:TechLeader) RETURN collect(d)")
    List<Employee> customQueryGetAvailableDevelopers();

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
            "LIMIT 3")
    List<TaskEntity> customQueryGetSuggestedTasks(@Param("developer") DeveloperEntity developer);

}
