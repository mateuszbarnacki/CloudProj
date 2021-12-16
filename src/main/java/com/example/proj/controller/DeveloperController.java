package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.dto.TeamDTO;
import com.example.proj.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping("/{name}/{surname}/{email}")
    public ResponseEntity<EmployeeDTO> getSingleRecord(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @PathVariable String email) {
        return developerService.getSingleRecord(name, surname, email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader record."));
    }

    @GetMapping("/team")
    public ResponseEntity<TeamDTO> getTeammates() {
        return ResponseEntity.ok(developerService.getTeammates());
    }

    @GetMapping("/current_task")
    public ResponseEntity<List<TaskDTO>> getCurrentTasks() {
        return ResponseEntity.ok(developerService.getCurrentTasks());
    }

    @GetMapping("/suggested_task")
    public ResponseEntity<List<TaskDTO>> getSuggestedTasks() {
        return ResponseEntity.ok(developerService.getSuggestedTasks());
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO employee = developerService.create(employeeDTO);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("")
    public ResponseEntity<EmployeeDTO> update(@RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO employee = developerService.update(employeeDTO);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        developerService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

}
