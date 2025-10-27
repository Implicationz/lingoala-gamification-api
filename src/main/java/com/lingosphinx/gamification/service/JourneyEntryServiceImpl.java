package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.*;
import com.lingosphinx.gamification.dto.JourneyEntryDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.JourneyEntryMapper;
import com.lingosphinx.gamification.repository.JourneyEntryRepository;
import com.lingosphinx.gamification.repository.JourneyItemRepository;
import com.lingosphinx.gamification.repository.JourneyItemTypeRepository;
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
public class JourneyEntryServiceImpl implements JourneyEntryService {

    private final JourneyRepository journeyRepository;
    private final JourneyEntryRepository journeyEntryRepository;
    private final JourneyEntryMapper journeyEntryMapper;
    private final JourneyItemRepository journeyItemRepository;
    private final JourneyItemTypeRepository journeyItemTypeRepository;

    @Override
    public JourneyEntryDto create(JourneyEntryDto journeyEntryDto) {
        var journeyEntry = journeyEntryMapper.toEntity(journeyEntryDto);
        var savedJourneyEntry = journeyEntryRepository.save(journeyEntry);
        log.info("JourneyEntry created successfully: id={}", savedJourneyEntry.getId());
        return journeyEntryMapper.toDto(savedJourneyEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public JourneyEntryDto readById(Long id) {
        var journeyEntry = journeyEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JourneyEntry not found"));
        return this.journeyEntryMapper.toDto(journeyEntry);
    }


    @Override
    @Transactional(readOnly = true)
    public List<JourneyEntryDto> readAll() {
        var result = journeyEntryRepository.findAll().stream()
                .map(journeyEntryMapper::toDto)
                .toList();
        log.info("All journeyEntrys read successfully, count={}", result.size());
        return result;
    }

    @Override
    public JourneyEntryDto update(Long id, JourneyEntryDto journeyEntryDto) {
        var existingJourneyEntry = journeyEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JourneyEntry not found"));

        journeyEntryMapper.toEntityFromDto(journeyEntryDto, existingJourneyEntry);
        log.info("JourneyEntry updated successfully: id={}", existingJourneyEntry.getId());
        return journeyEntryMapper.toDto(existingJourneyEntry);
    }

    @Override
    public void delete(Long id) {
        journeyEntryRepository.deleteById(id);
        log.info("JourneyEntry deleted successfully: id={}", id);
    }

    public Journey readCurrentUserJourney() {
        return Journey.builder().build();
    }

    @Override
    public void handle(GoalCompletedEvent event) {

        var goal = event.getGoal();

        var goalZone = goal.getZone();
        var contestant = goal.getContestant();
        var journey = journeyRepository
                .findByZoneAndContestant(goalZone, contestant)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found for contestant and zone"));

        var typeName = goalZone.getName();
        var reference = goal.getReference();

        var itemType = journeyItemTypeRepository
                .findByCategoryAndName(JourneyItemCategory.GOAL, typeName)
                .orElseGet(() -> {
                    var newType = JourneyItemType.builder()
                            .category(JourneyItemCategory.GOAL)
                            .name(typeName)
                            .build();
                    return journeyItemTypeRepository.save(newType);
                });

        var item = journeyItemRepository
                .findByTypeAndReference(itemType, reference)
                .orElseGet(() -> {
                    var newItem = JourneyItem.builder()
                            .type(itemType)
                            .reference(reference)
                            .image(goal.getImage())
                            .name(goal.getName())
                            .build();
                    return journeyItemRepository.save(newItem);
                });

        var journeyEntry = JourneyEntry.builder()
                .journey(journey)
                .type(JourneyEntryType.COMPLETED)
                .item(item)
                .build();
        journeyEntryRepository.save(journeyEntry);
    }
}