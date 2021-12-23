package com.example.proj.controller;

import com.example.proj.dto.DuetDTO;
import com.example.proj.dto.EmployeeDTO;
import com.example.proj.service.ProductOwnerService;
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
@RequestMapping("/product_owner")
@RequiredArgsConstructor
public class ProductOwnerController {
    private final ProductOwnerService productOwnerService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<EmployeeDTO> productOwners = productOwnerService.getAll();

        model.addAttribute("product_owners", productOwners);
        return "product-owner-list";
    }

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
    public String create(@ModelAttribute("product_owner") EmployeeDTO employeeDTO) {
        productOwnerService.create(employeeDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create product owner entity!"));
        return "redirect:/product_owner/list";
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

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        model.addAttribute("product_owner", employeeDTO);

        return "product-owner-form";
    }
}
