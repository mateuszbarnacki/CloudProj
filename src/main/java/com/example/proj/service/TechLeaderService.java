package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.mapper.EmployeeMapper;
import com.example.proj.mapper.TechLeaderMapper;
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

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return techLeaderRepository.findTechLeaderEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(employeeMapper::map);
    }

    public List<EmployeeDTO> getAvailableTechLeaders() {
        return techLeaderRepository.customQueryGetAvailableTechLeaders()
                .stream()
                .map(employeeMapper::map)
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
        return techLeaderRepository.customQueryAddDeveloper(techLeaderDTO.getName(), techLeaderDTO.getSurname(),
                        techLeaderDTO.getEmail(), developerDTO.getName(), developerDTO.getSurname(), developerDTO.getEmail())
                .map(employeeMapper::map);
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
                    .map(employeeMapper::map);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        TechLeaderEntity entity = techLeaderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find tech leader with id: " + id));
        techLeaderRepository.delete(entity);
    }

}
