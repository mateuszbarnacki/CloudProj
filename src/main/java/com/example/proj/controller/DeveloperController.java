package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("/available")
    public ResponseEntity<List<EmployeeDTO>> getAvailableDevelopers() {
        return ResponseEntity.ok(developerService.getAvailableDevelopers());
    }

    @PatchMapping("/startTask")
    public ResponseEntity<TaskDTO> startTask(@RequestBody @Valid TaskDTO taskDTO,
                                             @RequestBody @Valid EmployeeDTO employeeDTO) {
        return developerService.startTask(taskDTO, employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UnsupportedOperationException("Couldn't start this task."));
    }

    @PatchMapping("/finishTask")
    public ResponseEntity<TaskDTO> finishTask(@RequestBody @Valid TaskDTO taskDTO,
                                              @RequestBody @Valid EmployeeDTO employeeDTO) {
        return developerService.finishTask(taskDTO, employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UnsupportedOperationException("Couldn't finish this task."));
    }

    @GetMapping("/availableTasks")
    public ResponseEntity<List<TaskDTO>> getAllAvailableTasks() {
        return ResponseEntity.ok(developerService.getAllAvailableTasks());
    }

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(developerService.getTeammates(employeeDTO));
    }

    @GetMapping("/current_task")
    public ResponseEntity<List<TaskDTO>> getCurrentTasks(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(developerService.getCurrentTasks(employeeDTO));
    }

    @GetMapping("/suggested_task")
    public ResponseEntity<List<TaskDTO>> getSuggestedTasks(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(developerService.getSuggestedTasks(employeeDTO));
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return developerService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create developer entity!"));
    }

    @PutMapping("")
    public ResponseEntity<EmployeeDTO> update(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return developerService.update(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't update developer entity!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        developerService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

}
