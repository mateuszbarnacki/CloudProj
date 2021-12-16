package com.example.proj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private String name;
    private String surname;
    @NotNull
    private String email;
}
