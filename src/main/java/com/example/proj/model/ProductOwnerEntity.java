package com.example.proj.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("ProductOwner")
@Getter
@Setter
public class ProductOwnerEntity extends Employee {

    @Relationship(type = "COOPERATES_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<TechLeaderEntity> techLeaders = new HashSet<>();

    public ProductOwnerEntity() {
        super();
    }

}
