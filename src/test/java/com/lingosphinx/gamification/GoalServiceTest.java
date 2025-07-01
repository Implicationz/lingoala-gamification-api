package com.lingosphinx.gamification;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.repository.GoalRepository;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
import com.lingosphinx.gamification.service.GoalService;
import com.lingosphinx.gamification.service.GoalDefinitionService;
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
class GoalServiceTest {

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

    @BeforeEach
    void cleanUp() {
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

    @Test
    void createGoal_shouldPersistGoal() {
        var saved = createSampleGoal("TestGoal", "user-1");
        assertNotNull(saved.getId());
        assertEquals("TestGoal", saved.getDefinition().getName());
        assertEquals("user-1", saved.getUserId());
    }

    @Test
    void readById_shouldReturnPersistedGoal() {
        var saved = createSampleGoal("ReadGoal", "user-2");
        var found = goalService.readById(saved.getId());
        assertNotNull(found);
        assertEquals("ReadGoal", found.getDefinition().getName());
    }

    @Test
    void readAll_shouldReturnAllGoals() {
        createSampleGoal("Goal1", "user-1");
        createSampleGoal("Goal2", "user-2");
        List<GoalDto> all = goalService.readAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(g -> "Goal1".equals(g.getDefinition().getName())));
        assertTrue(all.stream().anyMatch(g -> "Goal2".equals(g.getDefinition().getName())));
    }

    @Test
    void delete_shouldRemoveGoal() {
        var saved = createSampleGoal("ToDelete", "user-3");
        goalService.delete(saved.getId());
        assertThrows(Exception.class, () -> goalService.readById(saved.getId()));
    }
}