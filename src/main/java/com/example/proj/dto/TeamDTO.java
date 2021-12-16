package com.example.proj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TeamDTO {
    @NotNull
    private List<EmployeeDTO> employees;
}
