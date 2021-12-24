package com.example.proj.controller;

import com.example.proj.dto.DuetDTO;
import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TechLeaderDTO;
import com.example.proj.service.TechLeaderService;
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
@RequestMapping("/tech_leader")
@RequiredArgsConstructor
public class TechLeaderController {
    private final TechLeaderService techLeaderService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<TechLeaderDTO> techLeaders = techLeaderService.getAll();

        model.addAttribute("tech_leaders", techLeaders);
        return "tech-leader-list";
    }

    @GetMapping("/{name}/{surname}/{email}")
    public ResponseEntity<TechLeaderDTO> getSingleRecord(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @PathVariable String email) {
        return techLeaderService.getSingleRecord(name, surname, email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader record."));
    }

    @GetMapping("/available")
    public ResponseEntity<List<TechLeaderDTO>> getAvailableTechLeaders() {
        return ResponseEntity.ok(techLeaderService.getAvailableTechLeaders());
    }

    @GetMapping("/team")
    public ResponseEntity<List<EmployeeDTO>> getTeammates(@RequestBody @Valid TechLeaderDTO techLeaderDTO) {
        return ResponseEntity.ok(techLeaderService.getTeammates(techLeaderDTO));
    }

    @PatchMapping("/developer")
    public ResponseEntity<TechLeaderDTO> addDeveloper(@RequestBody @Valid DuetDTO dto) {
        return techLeaderService.addDeveloper(dto.getFirst(), dto.getSecond())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't make relation between TechLeader and Developer!"));
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("tech_leader") TechLeaderDTO techLeaderDTO) {
        techLeaderService.create(techLeaderDTO)
                .orElseThrow(() -> new IllegalStateException("Couldn't create tech leader entity!"));
        return "redirect:/tech_leader/all";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        TechLeaderDTO techLeaderDTO = new TechLeaderDTO();

        model.addAttribute("tech_leader", techLeaderDTO);

        return "tech-leader-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("techLeaderId") Long id, Model model) {
        TechLeaderDTO techLeader = techLeaderService.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader with id: " + id));
        model.addAttribute("tech_leader", techLeader);
        return "tech-leader-form";
    }

    @GetMapping("/closeTeam")
    public String closeTeam(@RequestParam("techLeaderId") Long id) {
        techLeaderService.delete(id);

        return "redirect:/tech_leader/all";
    }
}
