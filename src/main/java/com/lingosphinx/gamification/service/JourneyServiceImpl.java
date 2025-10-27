package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Journey;
import com.lingosphinx.gamification.dto.JourneyActivationDto;
import com.lingosphinx.gamification.dto.JourneyDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.JourneyMapper;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import com.lingosphinx.gamification.repository.JourneyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JourneyServiceImpl implements JourneyService {

    private final ContestantService contestantService;
    private final GoalZoneRepository goalZoneRepository;
    private final JourneyRepository journeyRepository;
    private final JourneyMapper journeyMapper;

    @Override
    public JourneyDto create(JourneyDto journeyDto) {
        var journey = journeyMapper.toEntity(journeyDto);
        var savedJourney = journeyRepository.save(journey);
        log.info("Journey created successfully: id={}", savedJourney.getId());
        return journeyMapper.toDto(savedJourney);
    }

    @Override
    @Transactional(readOnly = true)
    public JourneyDto readById(Long id) {
        var journey = journeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));
        return this.journeyMapper.toDto(journey);
    }


    @Override
    @Transactional(readOnly = true)
    public List<JourneyDto> readAll() {
        var result = journeyRepository.findAll().stream()
                .map(journeyMapper::toDto)
                .toList();
        log.info("All journeys read successfully, count={}", result.size());
        return result;
    }

    @Override
    public JourneyDto update(Long id, JourneyDto journeyDto) {
        var existingJourney = journeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

        journeyMapper.toEntityFromDto(journeyDto, existingJourney);
        log.info("Journey updated successfully: id={}", existingJourney.getId());
        return journeyMapper.toDto(existingJourney);
    }

    @Override
    public void delete(Long id) {
        journeyRepository.deleteById(id);
        log.info("Journey deleted successfully: id={}", id);
    }

    @Override
    public JourneyActivationDto activate(JourneyActivationDto journeyActivation) {
        var contestant = contestantService.readCurrentContestant();
        var goalZone = goalZoneRepository.getReferenceById(journeyActivation.getJourney().getZone().getId());
        var journey = journeyRepository
                .findByZoneAndContestant(goalZone, contestant)
                .orElseGet(() -> {
                    var newJourney = Journey.builder()
                            .contestant(contestant)
                            .zone(goalZone)
                            .build();
                    return journeyRepository.save(newJourney);
                });
        log.info("Journey activated successfully: id={}", journey.getId());
        return JourneyActivationDto
                .builder()
                .journey(journeyMapper.toDto(journey))
                .build();
    }

}