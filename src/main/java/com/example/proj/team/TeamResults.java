package com.example.proj.team;

import com.example.proj.model.Developer;
import com.example.proj.model.ProductOwner;
import com.example.proj.model.TechLeader;
import org.neo4j.driver.Value;
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

        public Value getProductOwner() {
            return this.productOwner;
        }

        public Value getTechLeader() {
            return this.techLeader;
        }

        public Value getDeveloper() {
            return this.developer;
        }
    }

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByProductOwner(ProductOwner productOwner);

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByTechLeader(TechLeader techLeader);

    @Transactional(readOnly = true)
    Collection<Team> getTeammatesByDeveloper(Developer developer);
}
