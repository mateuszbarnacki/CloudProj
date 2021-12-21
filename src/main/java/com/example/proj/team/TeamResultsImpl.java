package com.example.proj.team;

import com.example.proj.model.Developer;
import com.example.proj.model.ProductOwner;
import com.example.proj.model.TechLeader;
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
    public Collection<Team> getTeammatesByProductOwner(ProductOwner productOwner) {
        return this.neo4jClient
                .query("MATCH (po: ProductOwner {name: $name, surname: $surname, email: $email}) " +
                        "OPTIONAL MATCH (po)-[:COOPERATES_WITH]->(tl:TechLeader) WITH po, collect(distinct tl) AS techLeaders " +
                        "OPTIONAL MATCH (po)-[:COOPERATES_WITH]->(:TechLeader)-[:GIVE_TASKS_FOR]->(d:Developer) " +
                        "RETURN collect(distinct po), techLeaders, collect(distinct d) AS developers"
                )
                .bind(productOwner.getName()).to(TeamResultsUtils.EMPLOYEE_NAME)
                .bind(productOwner.getSurname()).to(TeamResultsUtils.EMPLOYEE_SURNAME)
                .bind(productOwner.getEmail()).to(TeamResultsUtils.EMPLOYEE_EMAIL)
                .fetchAs(Team.class)
                .mappedBy(((typeSystem, resultSet) -> new Team(resultSet.get(0),
                        resultSet.get(1),
                        resultSet.get(2))))
                .all();
    }

    @Override
    public Collection<Team> getTeammatesByTechLeader(TechLeader techLeader) {
        return this.neo4jClient
                .query("MATCH (t: TechLeader {name: $name, surname: $surname, email: $email}) " +
                        "OPTIONAL MATCH (t)-[:GIVE_TASKS_FOR]->(d: Developer) WITH t, collect(distinct d) AS developers " +
                        "OPTIONAL MATCH (t)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
                        "RETURN collect(distinct po), collect(distinct t), developers"
                )
                .bind(techLeader.getName()).to(TeamResultsUtils.EMPLOYEE_NAME)
                .bind(techLeader.getSurname()).to(TeamResultsUtils.EMPLOYEE_SURNAME)
                .bind(techLeader.getEmail()).to(TeamResultsUtils.EMPLOYEE_EMAIL)
                .fetchAs(Team.class)
                .mappedBy((typeSystem, resultSet) -> new Team(resultSet.get(0),
                        resultSet.get(1),
                        resultSet.get(2)))
                .all();
    }

    @Override
    public Collection<Team> getTeammatesByDeveloper(Developer developer) {
        return this.neo4jClient
                .query("MATCH (d: Developer {name: $name, surname: $surname, email: $email}) " +
                        "OPTIONAL MATCH (d)<-[:GIVE_TASKS_FOR]-(t: TechLeader) WITH d, collect(distinct t) AS techLeaders " +
                        "OPTIONAL MATCH (d)<-[:GIVE_TASKS_FOR]-(:TechLeader)-[:GIVE_TASKS_FOR]->(od: Developer) " +
                        "WITH d, techLeaders, collect(distinct od) AS developers " +
                        "OPTIONAL MATCH (d)<-[:GIVE_TASKS_FOR]-(:TechLeader)<-[:COOPERATES_WITH]-(po:ProductOwner) " +
                        "RETURN collect(distinct po), techLeaders, developers + d"
                )
                .bind(developer.getName()).to(TeamResultsUtils.EMPLOYEE_NAME)
                .bind(developer.getSurname()).to(TeamResultsUtils.EMPLOYEE_SURNAME)
                .bind(developer.getEmail()).to(TeamResultsUtils.EMPLOYEE_EMAIL)
                .fetchAs(Team.class)
                .mappedBy(((typeSystem, resultSet) -> new Team(resultSet.get(0),
                        resultSet.get(1),
                        resultSet.get(2)))
                ).all();
    }
}
