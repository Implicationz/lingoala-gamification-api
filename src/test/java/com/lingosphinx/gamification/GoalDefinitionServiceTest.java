package com.lingosphinx.gamification;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalTypeDto;
import com.lingosphinx.gamification.dto.GoalZoneDto;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
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
class GoalDefinitionServiceTest {

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
    private GoalDefinitionService goalDefinitionService;

    @Autowired
    private GoalDefinitionRepository goalDefinitionRepository;

    @BeforeEach
    void cleanUp() {
        goalDefinitionRepository.deleteAll();
    }

    private GoalDefinitionDto createSampleGoalDefinition(String name) {
        GoalDefinitionDto dto = new GoalDefinitionDto();
        dto.setName(name);
        dto.setType(GoalTypeDto.builder().name("type_" + name).build());
        dto.setZone(GoalZoneDto.builder().name("zone_" + name).build());
        dto.setReference("ref_" + name);
        dto.setTarget(ProgressValue.valueOf(5));
        dto.setImage("img.png");
        return goalDefinitionService.create(dto);
    }

    @Test
    void createGoalDefinition_shouldPersistGoalDefinition() {
        var saved = createSampleGoalDefinition("TestGoal");
        assertNotNull(saved.getId());
        assertEquals("TestGoal", saved.getName());
        assertEquals("type_TestGoal", saved.getType());
        assertEquals("ref_TestGoal", saved.getReference());
    }

    @Test
    void readById_shouldReturnPersistedGoalDefinition() {
        var saved = createSampleGoalDefinition("ReadGoal");
        var found = goalDefinitionService.readById(saved.getId());
        assertNotNull(found);
        assertEquals("ReadGoal", found.getName());
    }

    @Test
    void delete_shouldRemoveGoalDefinition() {
        var saved = createSampleGoalDefinition("ToDelete");
        goalDefinitionService.delete(saved.getId());
        assertThrows(Exception.class, () -> goalDefinitionService.readById(saved.getId()));
    }
}