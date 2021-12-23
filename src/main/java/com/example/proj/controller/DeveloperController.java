package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<EmployeeDTO> developers = developerService.getAll();

        model.addAttribute("developers", developers);
        return "developer-list";
    }

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

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(developerService.getTeammates(employeeDTO));
    }

    @PostMapping("")
    public String create(@ModelAttribute("developer") EmployeeDTO employeeDTO) {
        developerService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create developer entity!"));
        return "redirect:/developer/list";
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

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        model.addAttribute("developer", employeeDTO);

        return "developer-form";
    }

}
