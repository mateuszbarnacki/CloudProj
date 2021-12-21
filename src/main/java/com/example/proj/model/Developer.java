package com.example.proj.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Developer")
@Getter
@Setter
public class Developer extends Employee {

    public Developer() {
        super();
    }

}
