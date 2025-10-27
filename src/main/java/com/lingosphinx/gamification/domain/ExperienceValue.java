package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lingosphinx.gamification.jackson.ExperienceValueDeserializer;
import com.lingosphinx.gamification.jackson.ExperienceValueSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ExperienceValueSerializer.class)
@JsonDeserialize(using = ExperienceValueDeserializer.class)
public class ExperienceValue {

    public static final ExperienceValue ZERO = ExperienceValue.valueOf(0);

    long value;

    public static ExperienceValue valueOf(long value) {
        return new ExperienceValue(value);
    }

    public ExperienceValue add(ExperienceValue experience) {
        return ExperienceValue.valueOf(this.value + experience.value);
    }
}

