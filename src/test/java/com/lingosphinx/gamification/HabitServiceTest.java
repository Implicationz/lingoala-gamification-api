package com.lingosphinx.gamification;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.RenewalType;
import com.lingosphinx.gamification.dto.*;
import com.lingosphinx.gamification.repository.ContestantRepository;
import com.lingosphinx.gamification.repository.HabitDefinitionRepository;
import com.lingosphinx.gamification.repository.HabitRepository;
import com.lingosphinx.gamification.service.ContestantService;
import com.lingosphinx.gamification.service.HabitDefinitionService;
import com.lingosphinx.gamification.service.HabitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class HabitServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ContestantService contestantService;
    @Autowired
    private ContestantRepository contestantRepository;
    @Autowired
    private HabitDefinitionService habitDefinitionService;
    @Autowired
    private HabitDefinitionRepository habitDefinitionRepository;
    @Autowired
    private HabitService habitService;
    @Autowired
    private HabitRepository habitRepository;

    @BeforeEach
    void cleanUp() {
        habitRepository.deleteAll();
        contestantRepository.deleteAll();
        habitDefinitionRepository.deleteAll();
    }

    private HabitDefinitionDto createSampleHabitDefinition(String name) {
        HabitDefinitionDto dto = new HabitDefinitionDto();
        dto.setName(name);
        dto.setZone(GoalZoneDto.builder().name("zone_" + name).build());
        dto.setTarget(ProgressValue.valueOf(5));
        dto.setImage("img.png");
        return habitDefinitionService.create(dto);
    }

    private ContestantDto createSampleContestant(UUID userId) {
        var contestant = ContestantDto.builder().userId(userId).build();
        return contestantService.create(contestant);
    }

    private HabitDto createSampleHabit(String goalDefName, UUID userId) {
        HabitDefinitionDto def = createSampleHabitDefinition(goalDefName);
        var streak = StreakDto.builder()
                .renewalType(RenewalType.NEVER)
                .duration(0L)
                .build();
        var contestant = createSampleContestant(userId);
        HabitDto dto = new HabitDto();
        dto.setContestant(contestant);
        dto.setDefinition(def);
        dto.setStreak(streak);
        return habitService.create(dto);
    }

    @Test
    void createHabit_shouldPersistHabit() {
        UUID userId = UUID.randomUUID();
        var saved = createSampleHabit("HabitGoal", userId);
        assertNotNull(saved.getId());
        assertEquals("HabitGoal", saved.getDefinition().getName());
        assertEquals(userId, saved.getContestant().getUserId());
        assertNotNull(saved.getStreak());
    }

    @Test
    void readById_shouldReturnPersistedHabit() {
        UUID userId = UUID.randomUUID();
        var saved = createSampleHabit("ReadHabit", userId);
        var found = habitService.readById(saved.getId());
        assertNotNull(found);
        assertEquals("ReadHabit", found.getDefinition().getName());
        assertEquals(userId, found.getContestant().getUserId());
    }

    @Test
    void readAll_shouldReturnAllHabits() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        createSampleHabit("Habit1", userId1);
        createSampleHabit("Habit2", userId2);
        List<HabitDto> all = habitService.readAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(h -> "Habit1".equals(h.getDefinition().getName())));
        assertTrue(all.stream().anyMatch(h -> "Habit2".equals(h.getDefinition().getName())));
    }

    @Test
    void delete_shouldRemoveHabit() {
        UUID userId = UUID.randomUUID();
        var saved = createSampleHabit("ToDeleteHabit", userId);
        habitService.delete(saved.getId());
        assertThrows(Exception.class, () -> habitService.readById(saved.getId()));
    }
}