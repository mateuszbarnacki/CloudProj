package com.example.proj.repository;

import com.example.proj.model.ProductOwner;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOwnerRepository extends Neo4jRepository<ProductOwner, Long> {

    @Query("MATCH (po:ProductOwner) WHERE id(po) = $productOwnerId " +
            "OPTIONAL MATCH (po)-[r:COOPERATES_WITH]->(:TechLeader) WITH po, r " +
            "OPTIONAL MATCH (po)-[r:COOPERATES_WITH]->(:TechLeader)-[nr:GIVE_TASKS_FOR]->(:Developer) " +
            "DELETE r, nr, po")
    void customQueryCloseTeam(@Param("productOwnerId") Long productOwnerId);

    @Query("MATCH (po:ProductOwner), (tl:TechLeader) " +
            "WHERE id(po) = $productOwnerId AND id(tl) = $techLeadId " +
            "CREATE (po)-[:COOPERATES_WITH]->(tl) " +
            "RETURN po")
    Optional<ProductOwner> customQueryAddTechLead(@Param("productOwnerId") Long productOwnerId,
                                                  @Param("techLeadId") Long techLeadId);
}
