package com.example.proj.repository;

import com.example.proj.model.Employee;
import com.example.proj.model.ProductOwnerEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface ProductOwnerRepository extends Neo4jRepository<ProductOwnerEntity, Long> {
    ProductOwnerEntity findProductOwnerEntityByNameAndSurnameAndEmail(String name, String surname, String email);

    @Query("")
    List<Employee> customQueryGetTeammates(ProductOwnerEntity productOwner);
}
