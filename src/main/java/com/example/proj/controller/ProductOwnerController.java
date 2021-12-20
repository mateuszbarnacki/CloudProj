package com.example.proj.controller;

import com.example.proj.dto.DuetDTO;
import com.example.proj.dto.EmployeeDTO;
import com.example.proj.service.ProductOwnerService;
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
@RequestMapping("/product_owner")
@RequiredArgsConstructor
public class ProductOwnerController {
    private final ProductOwnerService productOwnerService;

    @GetMapping("/{name}/{surname}/{email}")
    public ResponseEntity<EmployeeDTO> getSingleRecord(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @PathVariable String email) {
        return productOwnerService.getSingleRecord(name, surname, email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader record."));
    }

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(productOwnerService.getTeammates(employeeDTO));
    }

    @PatchMapping("/tech_leader")
    public ResponseEntity<EmployeeDTO> addTechLeader(@RequestBody @Valid DuetDTO duet) {
        return productOwnerService.addTechLead(duet.getFirst(), duet.getSecond())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return productOwnerService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create product owner entity!"));
    }

    @PutMapping("")
    public ResponseEntity<EmployeeDTO> update(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return productOwnerService.update(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't update product owner entity!"));
    }

    @DeleteMapping("/close")
    public ResponseEntity<?> closeTeam(@RequestBody @Valid EmployeeDTO employeeDTO) {
        productOwnerService.closeTeam(employeeDTO);
        return ResponseEntity.ok()
                .build();
    }
}
