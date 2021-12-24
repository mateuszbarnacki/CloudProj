package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.ProductOwnerDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOwnerService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerMapper productOwnerMapper;
    private final TeamResultsImpl teamResults;

    public List<ProductOwnerDTO> getAll() {
        return productOwnerRepository.findAll()
                .stream()
                .map(productOwnerMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ProductOwnerDTO> findById(Long id) {
        return productOwnerRepository.findById(id)
                .map(productOwnerMapper::map);
    }

    public Optional<ProductOwnerDTO> getSingleRecord(String name, String surname, String email) {
        return productOwnerRepository.findProductOwnerEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(productOwnerMapper::map);
    }

    public List<EmployeeDTO> getTeammates(ProductOwnerDTO productOwnerDTO) {
        ProductOwner entity = productOwnerMapper.map(productOwnerDTO);
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByProductOwner(entity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<ProductOwnerDTO> addTechLead(EmployeeDTO productOwnerDTO, EmployeeDTO techLeadDTO) {
        return productOwnerRepository.customQueryAddTechLead(productOwnerDTO.getName(), productOwnerDTO.getSurname(),
                        productOwnerDTO.getEmail(), techLeadDTO.getName(), techLeadDTO.getSurname(), techLeadDTO.getEmail())
                .map(productOwnerMapper::map);
    }

    public Optional<ProductOwnerDTO> create(ProductOwnerDTO productOwnerDTO) {
        ProductOwner entity = productOwnerMapper.map(productOwnerDTO);

        return Optional.of(productOwnerRepository.save(entity))
                .map(productOwnerMapper::map);
    }

    public void closeTeam(Long id) {
        productOwnerRepository.customQueryCloseTeam(id);
    }
}
