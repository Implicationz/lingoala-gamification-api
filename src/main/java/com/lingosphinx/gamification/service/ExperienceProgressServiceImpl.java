package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.*;
import com.lingosphinx.gamification.event.ExperienceProgressCreatedEvent;
import com.lingosphinx.gamification.event.ScoreProgressCreatedEvent;
import com.lingosphinx.gamification.repository.ContestantExperienceRepository;
import com.lingosphinx.gamification.repository.ContestantRepository;
import com.lingosphinx.gamification.repository.ExperienceProgressRepository;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExperienceProgressServiceImpl implements ExperienceProgressService {

    private final ContestantRepository contestantRepository;
    private final GoalZoneRepository goalZoneRepository;
    private final ContestantExperienceRepository contestantExperienceRepository;
    private final ExperienceProgressRepository experienceProgressRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void progress(Contestant contestant, GoalZone zone, ExperienceValue experience) {
        var foundContestant = contestantRepository.findById(contestant.getId()).orElseThrow();
        var found = foundContestant.getExperiences().stream()
                .filter(e -> e.getZone().equals(zone)).findFirst();

        var contestantExperience = found.orElseGet(() -> {
            var zoneReference = goalZoneRepository.getReferenceById(zone.getId());
            return ContestantExperience.builder()
                    .contestant(contestant)
                    .zone(zoneReference)
                    .build();
        });

        var experienceProgress = ExperienceProgress.builder()
                .contestant(contestantExperience)
                .experience(experience)
                .build();

        contestantExperience.apply(experienceProgress);
        contestantExperienceRepository.save(contestantExperience);

        experienceProgressRepository.save(experienceProgress);

        log.info("Experience progress updated for contestant: {}, zone: {}, new progress: {}",
                contestant.getId(), zone.getName(), experienceProgress.getExperience().getValue());
        eventPublisher.publishEvent(new ExperienceProgressCreatedEvent(experienceProgress));
    }


}