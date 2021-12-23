package com.example.proj.controller;

import com.example.proj.dto.DuetDTO;
import com.example.proj.dto.EmployeeDTO;
import com.example.proj.service.TechLeaderService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tech_leader")
@RequiredArgsConstructor
public class TechLeaderController {
    private final TechLeaderService techLeaderService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<EmployeeDTO> techLeaders = techLeaderService.getAll();

        model.addAttribute("tech_leaders", techLeaders);
        return "tech-leader-list";
    }

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
    public ResponseEntity<EmployeeDTO> addDeveloper(@RequestBody @Valid DuetDTO dto) {
        return techLeaderService.addDeveloper(dto.getFirst(), dto.getSecond())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
    }

    @PostMapping("")
    public String create(@ModelAttribute("tech_leader") EmployeeDTO employeeDTO) {
        techLeaderService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create tech leader entity!"));
        return "redirect:/tech_leader/list";
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

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        model.addAttribute("tech_leader", employeeDTO);

        return "tech-leader-form";
    }

}
