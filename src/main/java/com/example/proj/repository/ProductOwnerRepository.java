package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.ProductOwnerEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOwnerRepository extends Neo4jRepository<ProductOwnerEntity, Long> {
    ProductOwnerEntity findProductOwnerEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("MATCH " +
            "(po: ProductOwner {name: $productOwner.name, surname: $productOwner.surname, email: $productOwner.email})-" +
            "[:COOPERATES_WITH]->(tl:TechLeader)-" +
            "[:GIVE_TASKS_FOR]->(d:Developer) " +
            "RETURN collect(tl), collect(d)")
    List<Employee> customQueryGetTeammates(@Param("productOwner") ProductOwnerEntity productOwner);
}
