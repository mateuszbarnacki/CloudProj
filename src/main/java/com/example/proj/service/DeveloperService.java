package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.mapper.DeveloperMapper;
import com.example.proj.mapper.EmployeeMapper;
import com.example.proj.model.Developer;
import com.example.proj.repository.DeveloperRepository;
import com.example.proj.team.TeamResults;
import com.example.proj.team.TeamResultsImpl;
import com.example.proj.team.TeamResultsUtils;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;
    private final TeamResultsImpl teamResults;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDTO> getAll() {
        return developerRepository.findAll()
                .stream()
                .map(developerMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> findById(Long id) {
        return developerRepository.findById(id)
                .map(developerMapper::map);
    }

    public List<EmployeeDTO> getAvailableDevelopers() {
        return developerRepository.customQueryGetAvailableDevelopers()
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getTeammates(Long id) {
        Developer developerEntity = developerRepository.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find developer with id: " + id));
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByDeveloper(developerEntity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<EmployeeDTO> create(EmployeeDTO employeeDTO) {
        Developer entity = developerMapper.map(employeeDTO);

        return Optional.of(developerRepository.save(entity))
                .map(developerMapper::map);
    }

    public void delete(Long id) {
        Developer entity = developerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find developer with id: " + id));
        developerRepository.delete(entity);
    }

}
