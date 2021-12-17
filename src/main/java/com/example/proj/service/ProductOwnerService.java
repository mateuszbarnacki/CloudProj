package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.mapper.EmployeeMapper;
import com.example.proj.mapper.ProductOwnerMapper;
import com.example.proj.mapper.TaskMapper;
import com.example.proj.mapper.TechLeaderMapper;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TechLeaderEntity;
import com.example.proj.repository.ProductOwnerRepository;
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
public class ProductOwnerService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerMapper productOwnerMapper;
    private final EmployeeMapper employeeMapper;
    private final TaskMapper taskMapper;
    private final TechLeaderMapper techLeaderMapper;

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return productOwnerRepository.findProductOwnerEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(productOwnerMapper::map);
    }

    public List<EmployeeDTO> getTeammates(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = productOwnerMapper.map(employeeDTO);

        return productOwnerRepository.customQueryGetTeammates(entity)
                .stream()
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> generateReport(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = productOwnerMapper.map(employeeDTO);

        return productOwnerRepository.customQueryGenerateReport(entity)
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> addTechLead(EmployeeDTO productOwnerDTO, EmployeeDTO techLeadDTO) {
        ProductOwnerEntity productOwner = productOwnerMapper.map(productOwnerDTO);
        TechLeaderEntity techLeader = techLeaderMapper.map(techLeadDTO);

        return productOwnerRepository.customQueryAddTechLead(productOwner, techLeader)
                .map(productOwnerMapper::map);
    }

    public Optional<EmployeeDTO> create(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = productOwnerMapper.map(employeeDTO);

        return Optional.of(productOwnerRepository.save(entity))
                .map(productOwnerMapper::map);
    }

    public Optional<EmployeeDTO> update(EmployeeDTO employeeDTO) {
        if (Objects.nonNull(employeeDTO.getId())) {
            ProductOwnerEntity entity = productOwnerRepository.findById(employeeDTO.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Couldn't find product owner with id: " + employeeDTO.getId()));
            entity.setName(employeeDTO.getName());
            entity.setSurname(employeeDTO.getSurname());
            entity.setEmail(employeeDTO.getEmail());

            return Optional.of(productOwnerRepository.save(entity))
                    .map(productOwnerMapper::map);
        }
        return Optional.empty();
    }

    public void closeTeam(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = productOwnerMapper.map(employeeDTO);
        productOwnerRepository.customQueryCloseTeam(entity);
    }
}
