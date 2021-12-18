package com.example.proj.repository;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.Employee;
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

    @Query("MATCH (d: Developer {name: $developer.name, surname: $developer.surname, email: $developer.email})<-" +
            "[:GIVE_TASKS_FOR]-(t: TechLeader)-" +
            "[:GIVE_TASKS_FOR]->(od: Developer) " +
            "MATCH (d)<-[:GIVE_TASKS_FOR]-(:TechLeader)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
            "RETURN collect(t), collect(od), collect(po)")
    List<Employee> customQueryGetTeammates(@Param("developer") DeveloperEntity developer);
}
