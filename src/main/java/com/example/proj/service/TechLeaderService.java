package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.dto.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TechLeaderService {

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return null;
    }

    public List<EmployeeDTO> getAvailableTechLeaders(EmployeeDTO productOwner) {
        return null;
    }

    public TeamDTO getTeammates() {
        return null;
    }

    public List<TaskDTO> getCurrentTasks() {
        return null;
    }

    public List<TaskDTO> getSuggestedTasks() {
        return null;
    }

    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        return null;
    }

    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        return null;
    }

    public void delete(Long id) {

    }

}
