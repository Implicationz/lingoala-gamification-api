package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lingosphinx.gamification.jackson.ExperienceValueDeserializer;
import com.lingosphinx.gamification.jackson.ExperienceValueSerializer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@JsonSerialize(using = ExperienceValueSerializer.class)
@JsonDeserialize(using = ExperienceValueDeserializer.class)
public class ExperienceValue {

    public static final ExperienceValue ZERO = ExperienceValue.valueOf(0);

    final long value;

    public static ExperienceValue valueOf(long value) {
        return new ExperienceValue(value);
    }

    public ExperienceValue add(ExperienceValue experience) {
        return ExperienceValue.valueOf(this.value + experience.value);
    }
}

