package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOwnerRepository extends Neo4jRepository<ProductOwnerEntity, Long> {
    Optional<ProductOwnerEntity> findProductOwnerEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH " +
            "(po: ProductOwner {name: $productOwner.name, surname: $productOwner.surname, email: $productOwner.email})-" +
            "[:COOPERATES_WITH]->(tl:TechLeader)-" +
            "[:GIVE_TASKS_FOR]->(d:Developer) " +
            "RETURN collect(tl), collect(d)")
    List<Employee> customQueryGetTeammates(@Param("productOwner") ProductOwnerEntity productOwner);

    @Query("MATCH (po:ProductOwner {name: $productOwner.name, surname: $productOwner.surname, email: $productOwner.email})-" +
            "[r:COOPERATES_WITH]->(:TechLeader)-[nr:GIVE_TASKS_FOR]->(:Developer) " +
            "DELETE r, nr, po")
    void customQueryCloseTeam(@Param("productOwner") ProductOwnerEntity productOwner);

    @Query("MATCH (po:ProductOwner {name: $productOwner.name, surname: $productOwner.surname, email: $productOwner.email}), " +
            "(tl:TechLeader {name: $techLeader.name, surname: $techLeader.surname, email: $techLeader.email}) " +
            "CREATE (po)-[:COOPERATES_WITH]->(tl) " +
            "RETURN po")
    Optional<ProductOwnerEntity> customQueryAddTechLead(@Param("productOwner") ProductOwnerEntity productOwnerEntity,
                                                        @Param("techLeader") TechLeaderEntity techLeaderEntity);
}
