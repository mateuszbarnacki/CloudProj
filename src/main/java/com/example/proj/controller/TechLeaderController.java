package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TechLeaderDTO;
import com.example.proj.service.TechLeaderService;
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
@RequestMapping("/tech_leader")
@RequiredArgsConstructor
public class TechLeaderController {
    private final TechLeaderService techLeaderService;
    private static final String REDIRECTION = "redirect:/tech_leader/all";
    private static final String FORM = "tech-leader-form";

    @GetMapping("/all")
    public String getAll(Model model) {
        List<TechLeaderDTO> techLeaders = techLeaderService.getAll();

        model.addAttribute("tech_leaders", techLeaders);
        return "tech-leader-list";
    }

    @GetMapping("/available")
    public String getAvailableTechLeaders(@RequestParam("productOwnerId") Long id, Model model) {
        List<TechLeaderDTO> techLeaders = techLeaderService.getAvailableTechLeaders();

        model.addAttribute("availableTechLeaders", techLeaders);
        model.addAttribute("productOwnerId", id);
        return "tech-leader-available-list";
    }

    @GetMapping("/team")
    public String getTeammates(@RequestParam("techLeaderId") Long id, Model model) {
        List<EmployeeDTO> teamMates = techLeaderService.getTeammates(id);

        model.addAttribute("team", teamMates);
        return "tech-leader-team-list";
    }

    @GetMapping("/developer")
    public String addDeveloper(@RequestParam("techLeaderId") Long techLeaderId,
                               @RequestParam("developerId") Long developerId) {
        techLeaderService.addDeveloper(techLeaderId, developerId)
                .orElseThrow(() ->
                        new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
        return REDIRECTION;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("tech_leader") TechLeaderDTO techLeaderDTO) {
        techLeaderService.create(techLeaderDTO)
                .orElseThrow(() -> new IllegalStateException("Couldn't create tech leader entity!"));
        return REDIRECTION;
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        TechLeaderDTO techLeaderDTO = new TechLeaderDTO();

        model.addAttribute("tech_leader", techLeaderDTO);

        return FORM;
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("techLeaderId") Long id, Model model) {
        TechLeaderDTO techLeader = techLeaderService.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader with id: " + id));
        model.addAttribute("tech_leader", techLeader);
        return FORM;
    }

    @GetMapping("/closeTeam")
    public String closeTeam(@RequestParam("techLeaderId") Long id) {
        techLeaderService.delete(id);

        return REDIRECTION;
    }
}
