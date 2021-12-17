package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.DeveloperEntity;

public final class DeveloperMapper {
    private DeveloperMapper() {
    }

    public DeveloperEntity map(EmployeeDTO employeeDTO) {
        DeveloperEntity entity = new DeveloperEntity();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public EmployeeDTO map(DeveloperEntity developerEntity) {
        return new EmployeeDTO.Builder()
                .name(developerEntity.getName())
                .surname(developerEntity.getSurname())
                .email(developerEntity.getEmail())
                .build();
    }
}
