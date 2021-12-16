package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    public List<EmployeeDTO> getAllEmployees() {
        return null;
    }

    public Optional<EmployeeDTO> getEmployeeByEmailAddress(String email) {
        return null;
    }

    public TeamDTO getTeammatesByEmployeeEmailAddress(String email) {
        return null;
    }

    public List<TeamDTO> getAllTeams() {
        return null;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        return null;
    }

    public EmployeeDTO updateEmployee(String email, EmployeeDTO employee) {
        return null;
    }

    public void deleteEmployee(String email) {

    }
}
