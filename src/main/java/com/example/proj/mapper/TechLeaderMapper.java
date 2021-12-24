package com.example.proj.mapper;

import com.example.proj.dto.TechLeaderDTO;
import com.example.proj.model.TechLeader;
import org.springframework.stereotype.Component;

@Component
public final class TechLeaderMapper {
    private TechLeaderMapper() {
    }

    public TechLeader map(TechLeaderDTO techLeaderDTO) {
        TechLeader entity = new TechLeader();

        entity.setId(techLeaderDTO.getId());
        entity.setName(techLeaderDTO.getName());
        entity.setSurname(techLeaderDTO.getSurname());
        entity.setEmail(techLeaderDTO.getEmail());
        entity.setDevelopers(techLeaderDTO.getDevelopers());
        entity.setProductOwners(techLeaderDTO.getProductOwners());
        return entity;
    }

    public TechLeaderDTO map(TechLeader entity) {
        return new TechLeaderDTO.Builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .developers(entity.getDevelopers())
                .productOwners(entity.getProductOwners())
                .build();
    }
}
