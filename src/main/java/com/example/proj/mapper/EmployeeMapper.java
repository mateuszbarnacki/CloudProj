package com.example.proj.mapper;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.model.Employee;
import org.springframework.stereotype.Component;

@Component
public final class EmployeeMapper {
    private EmployeeMapper() {

    }

    public EmployeeDTO map(Employee employee) {
        return new EmployeeDTO.Builder()
                .name(employee.getName())
                .surname(employee.getSurname())
                .email(employee.getEmail())
                .build();
    }
}
