package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TaskEntity;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechLeaderRepository extends Neo4jRepository<TechLeaderEntity, Long> {
    TechLeaderEntity findTechLeaderEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH (tl: TechLeader) WHERE NOT (tl)<-[:COOPERATES_WITH]-(:ProductOwner) RETURN collect(tl)")
    List<TechLeaderEntity> customQueryGetAvailableTechLeaders(ProductOwnerEntity productOwner);

    @Query("MATCH (t: TechLeader {name: $techLeader.name, surname: $techLeader.surname, email: $techLeader.email})-" +
            "[:GIVE_TASKS_FOR]->(d: Developer) " +
            "MATCH (t)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
            "RETURN collect(d), collect(po)")
    List<Employee> customQueryGetTeammates(@Param("techLeader") TechLeaderEntity techLeaderEntity);

    @Query("MATCH (t:Task {status:\"ACTIVE\"})-" +
            "[:SOLVED_BY]->(:TechLeader {name: $techLeader.name, surname: $techLeader.surname}) " +
            "RETURN collect(t)")
    List<TaskEntity> customQueryGetCurrentTasks(@Param("techLeader") TechLeaderEntity techLeaderEntity);

    @Query("")
    List<TaskEntity> customQueryGetSuggestedTasks(TechLeaderEntity techLeaderEntity);

}
