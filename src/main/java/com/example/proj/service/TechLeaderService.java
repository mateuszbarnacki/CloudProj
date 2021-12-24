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

    public Optional<TechLeaderDTO> getSingleRecord(String name, String surname, String email) {
        return techLeaderRepository.findTechLeaderEntityByNameAndSurnameAndEmail(name, surname, email)
                .map(techLeaderMapper::map);
    }

    public List<TechLeaderDTO> getAvailableTechLeaders() {
        return techLeaderRepository.customQueryGetAvailableTechLeaders()
                .stream()
                .map(techLeaderMapper::map)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getTeammates(TechLeaderDTO techLeaderDTO) {
        TechLeader entity = techLeaderMapper.map(techLeaderDTO);
        List<TeamResults.Team> data = new ArrayList<>(teamResults.getTeammatesByTechLeader(entity));
        return TeamResultsUtils.retrieveEmployeesDataFromTeammatesResultSet(data);
    }

    public Optional<TechLeaderDTO> addDeveloper(EmployeeDTO techLeaderDTO, EmployeeDTO developerDTO) {
        return techLeaderRepository.customQueryAddDeveloper(techLeaderDTO.getName(), techLeaderDTO.getSurname(),
                        techLeaderDTO.getEmail(), developerDTO.getName(), developerDTO.getSurname(), developerDTO.getEmail())
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
