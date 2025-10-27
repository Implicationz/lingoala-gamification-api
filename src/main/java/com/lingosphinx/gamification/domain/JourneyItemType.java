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
        uniqueConstraints = @UniqueConstraint(columnNames = {"category", "name"}),
        indexes = {
                @Index(name = "idx_journey_item_type_category", columnList = "category"),
                @Index(name = "idx_journey_item_type_category_name", columnList = "category, name")
        }
)
public class JourneyItemType extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JourneyItemCategory category;

    @Column(nullable = false)
    private String name;

}
