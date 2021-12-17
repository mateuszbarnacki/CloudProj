package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.ProductOwnerEntity;

public final class ProductOwnerMapper {
    private ProductOwnerMapper() {
    }

    public static ProductOwnerEntity map(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = new ProductOwnerEntity();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public static EmployeeDTO map(ProductOwnerEntity entity) {
        return new EmployeeDTO.Builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .build();
    }
}
