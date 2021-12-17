package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.TechLeaderEntity;

public final class TechLeaderMapper {
    private TechLeaderMapper() {
    }

    public TechLeaderEntity map(EmployeeDTO employeeDTO) {
        TechLeaderEntity entity = new TechLeaderEntity();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public EmployeeDTO map(TechLeaderEntity entity) {
        return new EmployeeDTO.Builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .build();
    }
}
