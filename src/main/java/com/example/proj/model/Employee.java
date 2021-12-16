package com.example.proj.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@Getter
@Setter
public abstract class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String surname;
    private String email;

    public Employee(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
