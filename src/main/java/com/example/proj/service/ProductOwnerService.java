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
import org.neo4j.driver.exceptions.NoSuchRecordException;
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

    public List<EmployeeDTO> getTeammates(Long id) {
        ProductOwner entity = productOwnerRepository.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find product owner with id: " + id));
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByProductOwner(entity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<ProductOwnerDTO> addTechLead(Long productOwnerId, Long techLeadId) {
        return productOwnerRepository.customQueryAddTechLead(productOwnerId, techLeadId)
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
