package com.lingosphinx.gamification;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.RenewalType;
import com.lingosphinx.gamification.domain.Streak;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.dto.StreakDto;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
import com.lingosphinx.gamification.repository.GoalRepository;
import com.lingosphinx.gamification.repository.HabitRepository;
import com.lingosphinx.gamification.service.GoalDefinitionService;
import com.lingosphinx.gamification.service.GoalService;
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
    private GoalService goalService;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private GoalDefinitionService goalDefinitionService;
    @Autowired
    private GoalDefinitionRepository goalDefinitionRepository;
    @Autowired
    private HabitService habitService;
    @Autowired
    private HabitRepository habitRepository;

    @BeforeEach
    void cleanUp() {
        habitRepository.deleteAll();
        goalRepository.deleteAll();
        goalDefinitionRepository.deleteAll();
    }

    private GoalDefinitionDto createSampleGoalDefinition(String name) {
        GoalDefinitionDto dto = new GoalDefinitionDto();
        dto.setName(name);
        dto.setType("type_" + name);
        dto.setReference("ref_" + name);
        dto.setWorth(10);
        dto.setTarget(ProgressValue.valueOf(5));
        dto.setRenewalType("NEVER");
        dto.setImage("img.png");
        return goalDefinitionService.create(dto);
    }

    private GoalDto createSampleGoal(String goalDefName, String userId) {
        GoalDefinitionDto def = createSampleGoalDefinition(goalDefName);
        GoalDto dto = new GoalDto();
        dto.setDefinition(def);
        dto.setUserId(userId);
        dto.setProgress(ProgressValue.valueOf(5));
        return goalService.create(dto);
    }

    private HabitDto createSampleHabit(String goalDefName, String userId) {
        GoalDto goal = createSampleGoal(goalDefName, userId);
        var streak = StreakDto.builder()
                .renewalType(RenewalType.NEVER)
                .duration(0L)
                .build();
        HabitDto dto = new HabitDto();
        dto.setGoal(goal);
        dto.setStreak(streak);
        return habitService.create(dto);
    }

    @Test
    void createHabit_shouldPersistHabit() {
        var saved = createSampleHabit("HabitGoal", "user-1");
        assertNotNull(saved.getId());
        assertEquals("HabitGoal", saved.getGoal().getDefinition().getName());
        assertNotNull(saved.getStreak());
    }

    @Test
    void readById_shouldReturnPersistedHabit() {
        var saved = createSampleHabit("ReadHabit", "user-2");
        var found = habitService.readById(saved.getId());
        assertNotNull(found);
        assertEquals("ReadHabit", found.getGoal().getDefinition().getName());
    }

    @Test
    void readAll_shouldReturnAllHabits() {
        createSampleHabit("Habit1", "user-1");
        createSampleHabit("Habit2", "user-2");
        List<HabitDto> all = habitService.readAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(h -> "Habit1".equals(h.getGoal().getDefinition().getName())));
        assertTrue(all.stream().anyMatch(h -> "Habit2".equals(h.getGoal().getDefinition().getName())));
    }

    @Test
    void delete_shouldRemoveHabit() {
        var saved = createSampleHabit("ToDeleteHabit", "user-3");
        habitService.delete(saved.getId());
        assertThrows(Exception.class, () -> habitService.readById(saved.getId()));
    }
}