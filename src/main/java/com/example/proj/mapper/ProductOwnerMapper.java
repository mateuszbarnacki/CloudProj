package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.ProductOwner;
import org.springframework.stereotype.Component;

@Component
public final class ProductOwnerMapper {
    private ProductOwnerMapper() {
    }

    public ProductOwner map(EmployeeDTO employeeDTO) {
        ProductOwner entity = new ProductOwner();

        entity.setName(employeeDTO.getName());
        entity.setSurname(employeeDTO.getSurname());
        entity.setEmail(employeeDTO.getEmail());
        return entity;
    }

    public EmployeeDTO map(ProductOwner entity) {
        return new EmployeeDTO.Builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .build();
    }
}
