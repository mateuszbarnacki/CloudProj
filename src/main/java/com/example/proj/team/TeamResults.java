package com.example.proj.team;

import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TechLeaderEntity;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.ListValue;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface TeamResults {

    class Team {
        private final Value productOwner;
        private final Value techLeader;
        private final Value developer;

        Team(Value productOwnerEntity, Value techLeaderEntity, Value developerEntity) {
            this.productOwner = productOwnerEntity;
            this.techLeader = techLeaderEntity;
            this.developer = developerEntity;
        }
    }

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByProductOwner(ProductOwnerEntity productOwner);

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByTechLeader(TechLeaderEntity techLeader);

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByDeveloper(DeveloperEntity developer);
}
