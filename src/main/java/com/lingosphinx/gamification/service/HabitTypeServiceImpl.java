package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.HabitTypeDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.HabitTypeMapper;
import com.lingosphinx.gamification.repository.HabitTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitTypeServiceImpl implements HabitTypeService {

    private final HabitTypeRepository habitTypeRepository;
    private final HabitTypeMapper habitTypeMapper;

    @Override
    public HabitTypeDto create(HabitTypeDto habitTypeDto) {
        var habitType = habitTypeMapper.toEntity(habitTypeDto);
        var savedHabitType = habitTypeRepository.save(habitType);
        log.info("HabitType created successfully: id={}", savedHabitType.getId());
        return habitTypeMapper.toDto(savedHabitType);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitTypeDto readById(Long id) {
        var habitType = habitTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitType not found"));
        log.info("HabitType read successfully: id={}", id);
        return habitTypeMapper.toDto(habitType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitTypeDto> readAll() {
        var result = habitTypeRepository.findAll().stream()
                .map(habitTypeMapper::toDto)
                .toList();
        log.info("All habitTypes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public HabitTypeDto update(Long id, HabitTypeDto habitTypeDto) {
        var existingHabitType = habitTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitType not found"));

        habitTypeMapper.toEntityFromDto(habitTypeDto, existingHabitType);
        log.info("HabitType updated successfully: id={}", existingHabitType.getId());
        return habitTypeMapper.toDto(existingHabitType);
    }

    @Override
    public void delete(Long id) {
        habitTypeRepository.deleteById(id);
        log.info("HabitType deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "habitTypeReadByName", key = "#name")
    public HabitTypeDto readByName(String name) {
        var habitType = habitTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("HabitType not found for name=" + name + "."));
        log.info("HabitType read by name: name={}, id={}", name, habitType.getId());
        return habitTypeMapper.toDto(habitType);
    }
}