package com.example.proj.dto;

import com.example.proj.common.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    @NotNull
    private String projectName;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Status status;
    private Set<EmployeeDTO> creators;
    private Set<EmployeeDTO> solvers;
}
