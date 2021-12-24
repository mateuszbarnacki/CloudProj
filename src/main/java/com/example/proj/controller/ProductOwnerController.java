package com.example.proj.controller;

import com.example.proj.dto.DuetDTO;
import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.ProductOwnerDTO;
import com.example.proj.service.ProductOwnerService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product_owner")
@RequiredArgsConstructor
public class ProductOwnerController {
    private final ProductOwnerService productOwnerService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<ProductOwnerDTO> productOwners = productOwnerService.getAll();

        model.addAttribute("product_owners", productOwners);
        return "product-owner-list";
    }

    @GetMapping("/{name}/{surname}/{email}")
    public ResponseEntity<ProductOwnerDTO> getSingleRecord(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @PathVariable String email) {
        return productOwnerService.getSingleRecord(name, surname, email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader record."));
    }

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid ProductOwnerDTO productOwnerDTO) {
        return ResponseEntity.ok(productOwnerService.getTeammates(productOwnerDTO));
    }

    @PatchMapping("/tech_leader")
    public ResponseEntity<ProductOwnerDTO> addTechLeader(@RequestBody @Valid DuetDTO duet) {
        return productOwnerService.addTechLead(duet.getFirst(), duet.getSecond())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product_owner") ProductOwnerDTO productOwnerDTO) {
        productOwnerService.create(productOwnerDTO)
                .orElseThrow(() -> new IllegalStateException("Couldn't create product owner entity!"));
        return "redirect:/product_owner/all";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        ProductOwnerDTO productOwnerDTO = new ProductOwnerDTO();

        model.addAttribute("product_owner", productOwnerDTO);

        return "product-owner-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("productOwnerId") Long id, Model model) {
        ProductOwnerDTO productOwner = productOwnerService.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find product owner with id: " + id));
        model.addAttribute("product_owner", productOwner);
        return "product-owner-form";
    }

    @GetMapping("/closeTeam")
    public String closeTeam(@RequestParam("productOwnerId") Long id) {
        productOwnerService.closeTeam(id);

        return "redirect:/product_owner/all";
    }
}
