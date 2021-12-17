package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.mapper.DeveloperMapper;
import com.example.proj.mapper.EmployeeMapper;
import com.example.proj.mapper.TaskMapper;
import com.example.proj.mapper.TechLeaderMapper;
import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.TaskEntity;
import com.example.proj.model.TechLeaderEntity;
import com.example.proj.repository.TechLeaderRepository;
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
public class TechLeaderService {
    private final TechLeaderRepository techLeaderRepository;
    private final TechLeaderMapper techLeaderMapper;
    private final EmployeeMapper employeeMapper;
    private final DeveloperMapper developerMapper;
    private final TaskMapper taskMapper;

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return techLeaderRepository.findTechLeaderEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(techLeaderMapper::map);
    }

    public List<EmployeeDTO> getAvailableTechLeaders() {
        return techLeaderRepository.customQueryGetAvailableTechLeaders()
                .stream()
                .map(techLeaderMapper::map)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getTeammates(EmployeeDTO employeeDTO) {
        TechLeaderEntity entity = techLeaderMapper.map(employeeDTO);
        return techLeaderRepository.customQueryGetTeammates(entity)
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> addDeveloper(EmployeeDTO techLeaderDTO, EmployeeDTO developerDTO) {
        TechLeaderEntity techLeader = techLeaderMapper.map(techLeaderDTO);
        DeveloperEntity developer = developerMapper.map(developerDTO);

        return techLeaderRepository.customQueryAddDeveloper(techLeader, developer)
                .map(developerMapper::map);
    }

    public Optional<TaskDTO> createTask(EmployeeDTO employeeDTO, TaskDTO taskDTO) {
        TechLeaderEntity techLeaderEntity = techLeaderMapper.map(employeeDTO);
        TaskEntity taskEntity = taskMapper.map(taskDTO);

        return techLeaderRepository.customQueryCreateTask(techLeaderEntity, taskEntity)
                .map(taskMapper::map);
    }

    public Optional<EmployeeDTO> create(EmployeeDTO employeeDTO) {
        TechLeaderEntity entity = techLeaderMapper.map(employeeDTO);

        return Optional.of(techLeaderRepository.save(entity))
                .map(techLeaderMapper::map);
    }

    public Optional<EmployeeDTO> update(EmployeeDTO employeeDTO) {
        if (Objects.nonNull(employeeDTO.getId())) {
            TechLeaderEntity entity = techLeaderRepository.findById(employeeDTO.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Couldn't find tech leader with id: " + employeeDTO.getId()));
            entity.setName(employeeDTO.getName());
            entity.setSurname(employeeDTO.getSurname());
            entity.setEmail(employeeDTO.getEmail());

            return Optional.of(techLeaderRepository.save(entity))
                    .map(techLeaderMapper::map);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        TechLeaderEntity entity = techLeaderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find tech leader with id: " + id));
        techLeaderRepository.delete(entity);
    }

}
