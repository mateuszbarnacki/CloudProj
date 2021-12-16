package com.example.proj.repository;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.Employee;
import com.example.proj.model.TaskEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface DeveloperRepository extends Neo4jRepository<DeveloperEntity, Long> {
    DeveloperEntity findDeveloperEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("")
    List<Employee> customQueryGetTeammates(DeveloperEntity developer);

    @Query("")
    List<TaskEntity> customQueryGetCurrentTasks(DeveloperEntity developer);

    @Query("")
    List<TaskEntity> customQueryGetSuggestedTasks(DeveloperEntity developer);
}
