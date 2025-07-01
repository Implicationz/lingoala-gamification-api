package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ProgressValueSerializer.class)
@JsonDeserialize(using = ProgressValueDeserializer.class)
public class ProgressValue {
    long value;

    public static ProgressValue valueOf(long value) {
        return new ProgressValue(value);
    }
}

