package com.example.proj.repository;

import com.example.proj.model.TechLeader;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechLeaderRepository extends Neo4jRepository<TechLeader, Long> {

    @Query("MATCH (tl: TechLeader) WHERE NOT (tl)<-[:COOPERATES_WITH]-(:ProductOwner) RETURN collect(tl)")
    List<TechLeader> customQueryGetAvailableTechLeaders();

    @Query("MATCH (tl: TechLeader), (d: Developer) " +
            "WHERE id(tl) = $techLeaderId AND id(d) = $developerId " +
            "CREATE (tl)-[:GIVE_TASKS_FOR]->(d) " +
            "RETURN tl")
    Optional<TechLeader> customQueryAddDeveloper(@Param("techLeaderId") Long techLeaderId,
                                                 @Param("developerId") Long developerId);

}
