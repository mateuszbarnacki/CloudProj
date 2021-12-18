package com.example.proj.team;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TechLeaderEntity;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TeamResultsImpl implements TeamResults {

    private final Neo4jClient neo4jClient;

    TeamResultsImpl(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public Collection<Team> getTeammatesByProductOwner(ProductOwnerEntity productOwner) {
        return this.neo4jClient
                .query("MATCH " +
                        "(po: ProductOwner {name: $name, surname: $surname, email: $email})-" +
                        "[:COOPERATES_WITH]->(tl:TechLeader)-" +
                        "[:GIVE_TASKS_FOR]->(d:Developer) " +
                        "RETURN collect(po), collect(tl), collect(d)"
                )
                .bind(productOwner.getName()).to("name")
                .bind(productOwner.getSurname()).to("surname")
                .bind(productOwner.getEmail()).to("email")
                .fetchAs(Team.class)
                .mappedBy(((typeSystem, record) -> new Team(record.get(0),
                        record.get(1),
                        record.get(2))))
                .all();
    }

    @Override
    public Collection<Team> getTeammatesByTechLeader(TechLeaderEntity techLeader) {
        return this.neo4jClient
                .query("MATCH (t: TechLeader {name: $name, surname: $surname, email: $email})-" +
                        "[:GIVE_TASKS_FOR]->(d: Developer) " +
                        "MATCH (t)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
                        "RETURN collect(po), collect(t), collect(d)"
                )
                .bind(techLeader.getName()).to("name")
                .bind(techLeader.getSurname()).to("surname")
                .bind(techLeader.getEmail()).to("email")
                .fetchAs(Team.class)
                .mappedBy(((typeSystem, record) -> new Team(record.get(0),
                        record.get(1),
                        record.get(2))))
                .all();
    }

    @Override
    public Collection<Team> getTeammatesByDeveloper(DeveloperEntity developer) {
        return this.neo4jClient
                .query("MATCH (d: Developer {name: $name, surname: $surname, email: $email})<-" +
                        "[:GIVE_TASKS_FOR]-(t: TechLeader)-" +
                        "[:GIVE_TASKS_FOR]->(od: Developer) " +
                        "MATCH (d)<-[:GIVE_TASKS_FOR]-(:TechLeader)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
                        "RETURN collect(po), collect(t), collect(od)"
                )
                .bind(developer.getName()).to("name")
                .bind(developer.getSurname()).to("surname")
                .bind(developer.getEmail()).to("email")
                .fetchAs(Team.class)
                .mappedBy(((typeSystem, record) -> new Team(record.get(0),
                         record.get(1),
                         record.get(2))))
                .all();
    }
}
