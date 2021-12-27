package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;
    private static final String REDIRECTION = "redirect:/developer/all";
    private static final String FORM = "developer-form";

    @GetMapping("/all")
    public String getAll(Model model) {
        List<EmployeeDTO> developers = developerService.getAll();

        model.addAttribute("developers", developers);
        return "developer-list";
    }

    @GetMapping("/available")
    public String getAvailableDevelopers(@RequestParam("techLeaderId") Long id, Model model) {
        List<EmployeeDTO> developers = developerService.getAvailableDevelopers();

        model.addAttribute("availableDevelopers", developers);
        model.addAttribute("techLeaderId", id);
        return "developer-available-list";
    }

    @GetMapping("/team")
    public String getTeammates(@RequestParam("developerId") Long id, Model model) {
        List<EmployeeDTO> teamMembers = developerService.getTeammates(id);

        model.addAttribute("team", teamMembers);
        return "developer-team-list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("developer") EmployeeDTO employeeDTO) {
        developerService.create(employeeDTO)
                .orElseThrow(() -> new IllegalStateException("Couldn't create developer entity!"));
        return REDIRECTION;
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        model.addAttribute("developer", employeeDTO);

        return FORM;
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("developerId") Long id, Model model) {
        EmployeeDTO developer = developerService.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find developer with id: " + id));
        model.addAttribute("developer", developer);
        return FORM;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("developerId") Long id) {
        developerService.delete(id);

        return REDIRECTION;
    }
}
