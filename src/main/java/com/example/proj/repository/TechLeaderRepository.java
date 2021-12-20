package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechLeaderRepository extends Neo4jRepository<TechLeaderEntity, Long> {
    Optional<TechLeaderEntity> findTechLeaderEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH (tl: TechLeader) WHERE NOT (tl)<-[:COOPERATES_WITH]-(:ProductOwner) RETURN collect(tl)")
    List<TechLeaderEntity> customQueryGetAvailableTechLeaders();

    @Query("MATCH (t: TechLeader {name: $techLeader.name, surname: $techLeader.surname, email: $techLeader.email})-" +
            "[:GIVE_TASKS_FOR]->(d: Developer) " +
            "MATCH (t)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
            "RETURN collect(d), collect(po)")
    List<Employee> customQueryGetTeammates(@Param("techLeader") TechLeaderEntity techLeaderEntity);

    @Query("MATCH (tl: TechLeader{name: $techLeaderName, surname: $techLeaderSurname, email: $techLeaderEmail}), " +
            "(d: Developer{name: $developerName, surname: $developerSurname, email: $developerEmail}) " +
            "CREATE (tl)-[:GIVE_TASKS_FOR]->(d) " +
            "RETURN tl")
    Optional<TechLeaderEntity> customQueryAddDeveloper(@Param("techLeaderName") String techLeaderName,
                                                       @Param("techLeaderSurname") String techLeaderSurname,
                                                       @Param("techLeaderEmail") String techLeaderEmail,
                                                       @Param("developerName") String developerName,
                                                       @Param("developerSurname") String developerSurname,
                                                       @Param("developerEmail") String developerEmail);

}
