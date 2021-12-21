package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.Developer;
import org.springframework.stereotype.Component;

@Component
public final class DeveloperMapper {
    private DeveloperMapper() {
    }

    public Developer map(EmployeeDTO employeeDTO) {
        Developer entity = new Developer();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public EmployeeDTO map(Developer developerEntity) {
        return new EmployeeDTO.Builder()
                .name(developerEntity.getName())
                .surname(developerEntity.getSurname())
                .email(developerEntity.getEmail())
                .build();
    }
}
