package com.example.proj.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("TechLeader")
@Getter
@Setter
public class TechLeader extends Employee {

    @Relationship(type = "GIVE_TASKS_FOR", direction = Relationship.Direction.OUTGOING)
    private Set<Developer> developers = new HashSet<>();
    @Relationship(type = "COOPERATES_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<ProductOwner> productOwners = new HashSet<>();

    public TechLeader() {
        super();
    }

}
