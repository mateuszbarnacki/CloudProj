package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TaskEntity;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface TechLeaderRepository extends Neo4jRepository<TechLeaderEntity, Long> {
    TechLeaderEntity findTechLeaderEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("")
    List<TechLeaderEntity> customQueryGetAvailableTechLeaders(ProductOwnerEntity productOwner);

    @Query("")
    List<Employee> customQueryGetAssociatedDevelopers(TechLeaderEntity techLeaderEntity);

    @Query("")
    List<Employee> customQueryGetAssociatedProductOwner(TechLeaderEntity techLeaderEntity);

    @Query("")
    List<TaskEntity> customQueryGetCurrentTasks(TechLeaderEntity techLeaderEntity);

    @Query("")
    List<TaskEntity> customQueryGetSuggestedTasks(TechLeaderEntity techLeaderEntity);

}
