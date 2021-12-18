package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.mapper.DeveloperMapper;
import com.example.proj.mapper.EmployeeMapper;
import com.example.proj.model.DeveloperEntity;
import com.example.proj.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;
    private final EmployeeMapper employeeMapper;

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return developerRepository.findDeveloperEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(employeeMapper::map);
    }

    public List<EmployeeDTO> getAvailableDevelopers() {
        return developerRepository.customQueryGetAvailableDevelopers()
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getTeammates(EmployeeDTO employeeDTO) {
        DeveloperEntity entity = developerMapper.map(employeeDTO);
        return developerRepository.customQueryGetTeammates(entity)
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> create(EmployeeDTO employeeDTO) {
        DeveloperEntity entity = developerMapper.map(employeeDTO);

        return Optional.of(developerRepository.save(entity))
                .map(developerMapper::map);
    }

    public Optional<EmployeeDTO> update(EmployeeDTO employeeDTO) {
        if (Objects.nonNull(employeeDTO.getId())) {
            DeveloperEntity entity = developerRepository.findById(employeeDTO.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Couldn't find developer with id: " + employeeDTO.getId()));
            entity.setName(employeeDTO.getName());
            entity.setSurname(employeeDTO.getSurname());
            entity.setEmail(employeeDTO.getEmail());

            return Optional.of(developerRepository.save(entity))
                    .map(employeeMapper::map);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        DeveloperEntity entity = developerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find developer with id: " + id));
        developerRepository.delete(entity);
    }
}
