package com.example.proj.repository;

import com.example.proj.model.ProductOwner;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOwnerRepository extends Neo4jRepository<ProductOwner, Long> {
    Optional<ProductOwner> findProductOwnerEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH (po:ProductOwner {name: $productOwnerName, surname: $productOwnerSurname, email: $productOwnerEmail}) " +
            "OPTIONAL MATCH (po)-[r:COOPERATES_WITH]->(:TechLeader) WITH po, r " +
            "OPTIONAL MATCH (po)-[r:COOPERATES_WITH]->(:TechLeader)-[nr:GIVE_TASKS_FOR]->(:Developer) " +
            "DELETE r, nr, po")
    void customQueryCloseTeam(@Param("productOwnerName") String productOwnerName,
                              @Param("productOwnerSurname") String productOwnerSurname,
                              @Param("productOwnerEmail") String productOwnerEmail);

    @Query("MATCH (po:ProductOwner {name: $productOwnerName, surname: $productOwnerSurname, email: $productOwnerEmail}), " +
            "(tl:TechLeader {name: $techLeaderName, surname: $techLeaderSurname, email: $techLeaderEmail}) " +
            "CREATE (po)-[:COOPERATES_WITH]->(tl) " +
            "RETURN po")
    Optional<ProductOwner> customQueryAddTechLead(@Param("productOwnerName") String productOwnerName,
                                                  @Param("productOwnerSurname") String productOwnerSurname,
                                                  @Param("productOwnerEmail") String email,
                                                  @Param("techLeaderName") String techLeaderName,
                                                  @Param("techLeaderSurname") String techLeaderSurname,
                                                  @Param("techLeaderEmail") String techLeaderEmail);
}
