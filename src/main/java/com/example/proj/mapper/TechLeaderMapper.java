package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.TechLeader;
import org.springframework.stereotype.Component;

@Component
public final class TechLeaderMapper {
    private TechLeaderMapper() {
    }

    public TechLeader map(EmployeeDTO employeeDTO) {
        TechLeader entity = new TechLeader();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public EmployeeDTO map(TechLeader entity) {
        return new EmployeeDTO.Builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .build();
    }
}
