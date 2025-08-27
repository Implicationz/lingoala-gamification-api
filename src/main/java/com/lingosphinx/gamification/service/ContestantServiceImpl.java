package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ContestantMapper;
import com.lingosphinx.gamification.repository.ContestantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ContestantServiceImpl implements ContestantService {

    private final UserService userService;
    private final ContestantRepository contestantRepository;
    private final ContestantMapper contestantMapper;

    @Override
    public ContestantDto registerCurrent() {
        var userId = this.userService.getCurrentUserId();
        var registered = this.contestantRepository.findByUserId(userId).orElseGet(() -> {
            var contestant = new Contestant();
            contestant.setUserId(userId);
            var savedContestant = contestantRepository.save(contestant);
            log.info("Contestant registered successfully: id={}", savedContestant.getId());
            return savedContestant;
        });
        return contestantMapper.toDto(registered);
    }

    @Override
    public ContestantDto create(ContestantDto contestantDto) {
        var contestant = contestantMapper.toEntity(contestantDto);
        var savedContestant = contestantRepository.save(contestant);
        log.info("Contestant created successfully: id={}", savedContestant.getId());
        return contestantMapper.toDto(savedContestant);
    }

    @Override
    @Transactional(readOnly = true)
    public ContestantDto readById(Long id) {
        var contestant = contestantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contestant not found"));
        return this.contestantMapper.toDto(contestant);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ContestantDto> readAll() {
        var result = contestantRepository.findAll().stream()
                .map(contestantMapper::toDto)
                .toList();
        log.info("All contestants read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ContestantDto update(Long id, ContestantDto contestantDto) {
        var existingContestant = contestantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contestant not found"));

        contestantMapper.toEntityFromDto(contestantDto, existingContestant);
        log.info("Contestant updated successfully: id={}", existingContestant.getId());
        return contestantMapper.toDto(existingContestant);
    }

    @Override
    public void delete(Long id) {
        contestantRepository.deleteById(id);
        log.info("Contestant deleted successfully: id={}", id);
    }

    @Override
    public Contestant readCurrentContestant() {
        var userId = this.userService.getCurrentUserId();
        return this.contestantRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Current Contestant not found"));
    }

}