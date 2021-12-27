package com.example.proj.repository;

import com.example.proj.model.Developer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends Neo4jRepository<Developer, Long> {

    @Query("MATCH (d: Developer) WHERE NOT (d)<-[:GIVE_TASKS_FOR]-(:TechLeader) RETURN collect(d)")
    List<Developer> customQueryGetAvailableDevelopers();

}
