package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TechLeaderDTO;
import com.example.proj.mapper.TechLeaderMapper;
import com.example.proj.model.TechLeader;
import com.example.proj.repository.TechLeaderRepository;
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
public class TechLeaderService {
    private final TechLeaderRepository techLeaderRepository;
    private final TechLeaderMapper techLeaderMapper;
    private final TeamResultsImpl teamResults;

    public List<TechLeaderDTO> getAll() {
        return techLeaderRepository.findAll()
                .stream()
                .map(techLeaderMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<TechLeaderDTO> findById(Long id) {
        return techLeaderRepository.findById(id)
                .map(techLeaderMapper::map);
    }

    public List<TechLeaderDTO> getAvailableTechLeaders() {
        return techLeaderRepository.customQueryGetAvailableTechLeaders()
                .stream()
                .map(techLeaderMapper::map)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getTeammates(Long id) {
        TechLeader entity = techLeaderRepository.findById(id)
                .orElseThrow(() -> new NoSuchRecordException("Couldn't find tech leader with id: " + id));
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByTechLeader(entity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<TechLeaderDTO> addDeveloper(Long techLeaderId, Long developerId) {
        return techLeaderRepository.customQueryAddDeveloper(techLeaderId, developerId)
                .map(techLeaderMapper::map);
    }

    public Optional<TechLeaderDTO> create(TechLeaderDTO techLeaderDTO) {
        TechLeader entity = techLeaderMapper.map(techLeaderDTO);

        return Optional.of(techLeaderRepository.save(entity))
                .map(techLeaderMapper::map);
    }

    public void delete(Long id) {
        TechLeader entity = techLeaderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find tech leader with id: " + id));
        techLeaderRepository.delete(entity);
    }

}
