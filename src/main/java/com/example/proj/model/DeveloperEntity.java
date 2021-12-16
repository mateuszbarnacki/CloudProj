package com.example.proj.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Developer")
@Getter
@Setter
public class DeveloperEntity extends Employee {

    public DeveloperEntity(String name, String surname, String email) {
        super(name, surname, email);
    }

}
