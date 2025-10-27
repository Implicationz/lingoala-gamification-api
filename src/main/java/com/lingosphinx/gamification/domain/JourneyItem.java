package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"type_id", "reference"}),
        indexes = {
                @Index(name = "idx_journey_item_type_reference", columnList = "type_id,reference")
        }
)
public class JourneyItem extends BaseEntity {

    @ManyToOne(optional = false)
    private JourneyItemType type;

    private String name;
    private String reference;
    private String image;
}