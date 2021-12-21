package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.mapper.ProductOwnerMapper;
import com.example.proj.model.ProductOwner;
import com.example.proj.repository.ProductOwnerRepository;
import com.example.proj.team.TeamResults;
import com.example.proj.team.TeamResultsImpl;
import com.example.proj.team.TeamResultsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOwnerService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerMapper productOwnerMapper;
    private final TeamResultsImpl teamResults;

    public Optional<EmployeeDTO> getSingleRecord(String name, String surname, String email) {
        return productOwnerRepository.findProductOwnerEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(productOwnerMapper::map);
    }

    public List<EmployeeDTO> getTeammates(EmployeeDTO employeeDTO) {
        ProductOwner entity = productOwnerMapper.map(employeeDTO);
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByProductOwner(entity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<EmployeeDTO> addTechLead(EmployeeDTO productOwnerDTO, EmployeeDTO techLeadDTO) {
        return productOwnerRepository.customQueryAddTechLead(productOwnerDTO.getName(), productOwnerDTO.getSurname(),
                        productOwnerDTO.getEmail(), techLeadDTO.getName(), techLeadDTO.getSurname(), techLeadDTO.getEmail())
                .map(productOwnerMapper::map);
    }

    public Optional<EmployeeDTO> create(EmployeeDTO employeeDTO) {
        ProductOwner entity = productOwnerMapper.map(employeeDTO);

        return Optional.of(productOwnerRepository.save(entity))
                .map(productOwnerMapper::map);
    }

    public Optional<EmployeeDTO> update(EmployeeDTO employeeDTO) {
        if (Objects.nonNull(employeeDTO.getId())) {
            ProductOwner entity = productOwnerRepository.findById(employeeDTO.getId())
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
        ProductOwner entity = productOwnerMapper.map(employeeDTO);
        productOwnerRepository.customQueryCloseTeam(entity.getName(), entity.getSurname(), entity.getEmail());
    }
}
