package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExperienceProgress extends BaseEntity {

    @ManyToOne(optional = false)
    private ContestantExperience contestant;
    private ExperienceValue experience;

}