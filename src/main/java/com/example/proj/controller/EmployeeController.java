package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TeamDTO;
import com.example.proj.service.EmployeeService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmailAddress(@PathVariable String email) {
        return employeeService.getEmployeeByEmailAddress(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find employee with this email address."));
    }

    @GetMapping("/team/{email}")
    public ResponseEntity<TeamDTO> getTeammatesByEmployeeEmailAddress(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.getTeammatesByEmployeeEmailAddress(email));
    }

    @GetMapping("/team")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(employeeService.getAllTeams());
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employee) {
        EmployeeDTO employeeDTO = employeeService.createEmployee(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeDTO> updateEmployeeData(@PathVariable String email,
                                                          @RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO employee = employeeService.updateEmployee(email, employeeDTO);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
        return ResponseEntity.ok()
                .build();
    }
}
