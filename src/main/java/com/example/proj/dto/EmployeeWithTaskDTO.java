package com.example.proj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeWithTaskDTO {
    private EmployeeDTO employee;
    private TaskDTO task;
}
