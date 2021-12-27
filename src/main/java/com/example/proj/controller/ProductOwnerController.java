package com.example.proj.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/product_owner")
@RequiredArgsConstructor
public class ProductOwnerController {
    private final ProductOwnerService productOwnerService;
    private static final String REDIRECTION = "redirect:/product_owner/all";
    private static final String FORM = "product-owner-form";

    @GetMapping("/all")
    public String getAll(Model model) {
        List<ProductOwnerDTO> productOwners = productOwnerService.getAll();

        model.addAttribute("product_owners", productOwners);
        return "product-owner-list";
    }

    @GetMapping("/team")
    public String getTeammates(@RequestParam("productOwnerId") Long id, Model model) {
        List<EmployeeDTO> teamMates = productOwnerService.getTeammates(id);

        model.addAttribute("team", teamMates);
        return "product-owner-team-list";
    }

    @GetMapping("/tech_leader")
    public String addTechLeader(@RequestParam("productOwnerId") Long productOwnerId,
                                @RequestParam("techLeaderId") Long techLeaderId) {
       productOwnerService.addTechLead(productOwnerId, techLeaderId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
       return REDIRECTION;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product_owner") ProductOwnerDTO productOwnerDTO) {
        productOwnerService.create(productOwnerDTO)
                .orElseThrow(() -> new IllegalStateException("Couldn't create product owner entity!"));
        return REDIRECTION;
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        ProductOwnerDTO productOwnerDTO = new ProductOwnerDTO();

        model.addAttribute("product_owner", productOwnerDTO);

        return FORM;
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("productOwnerId") Long id, Model model) {
        ProductOwnerDTO productOwner = productOwnerService.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find product owner with id: " + id));
        model.addAttribute("product_owner", productOwner);
        return FORM;
    }

    @GetMapping("/closeTeam")
    public String closeTeam(@RequestParam("productOwnerId") Long id) {
        productOwnerService.closeTeam(id);

        return REDIRECTION;
    }
}
