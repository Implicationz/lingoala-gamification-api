package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ProgressValueSerializer.class)
@JsonDeserialize(using = ProgressValueDeserializer.class)
public class ProgressValue {

    public static final ProgressValue ZERO = ProgressValue.valueOf(0);

    long value;

    public static ProgressValue valueOf(long value) {
        return new ProgressValue(value);
    }
}

