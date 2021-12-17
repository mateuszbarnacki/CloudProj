package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.service.TechLeaderService;
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
@RequestMapping("/tech_leader")
@RequiredArgsConstructor
public class TechLeaderController {
    private final TechLeaderService techLeaderService;

    @GetMapping("/{name}/{surname}/{email}")
    public ResponseEntity<EmployeeDTO> getSingleRecord(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @PathVariable String email) {
        return techLeaderService.getSingleRecord(name, surname, email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader record."));
    }

    @GetMapping("/available")
    public ResponseEntity<List<EmployeeDTO>> getAvailableTechLeaders() {
        return ResponseEntity.ok(techLeaderService.getAvailableTechLeaders());
    }

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(techLeaderService.getTeammates(employeeDTO));
    }

    @PatchMapping("/developer")
    public ResponseEntity<EmployeeDTO> addDeveloper(@RequestBody @Valid EmployeeDTO techLeader,
                                                    @RequestBody @Valid EmployeeDTO developer) {
        return techLeaderService.addDeveloper(techLeader, developer)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid EmployeeDTO employeeDTO,
                                              @RequestBody @Valid TaskDTO taskDTO) {
        return techLeaderService.createTask(employeeDTO, taskDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create task!"));
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return techLeaderService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create tech leader entity!"));
    }

    @PutMapping("")
    public ResponseEntity<EmployeeDTO> update(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return techLeaderService.update(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't update tech leader entity!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        techLeaderService.delete(id);
        return ResponseEntity.ok()
                .build();
    }
}
