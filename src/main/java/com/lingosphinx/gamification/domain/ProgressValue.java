package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lingosphinx.gamification.jackson.ProgressValueDeserializer;
import com.lingosphinx.gamification.jackson.ProgressValueSerializer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@JsonSerialize(using = ProgressValueSerializer.class)
@JsonDeserialize(using = ProgressValueDeserializer.class)
public class ProgressValue {

    public static final ProgressValue ZERO = ProgressValue.valueOf(0);

    final long value;

    public static ProgressValue valueOf(long value) {
        return new ProgressValue(value);
    }

    public boolean isGreaterOrEqual(ProgressValue other) {
        return this.value >= other.value;
    }

    public boolean isGreater(ProgressValue other) {
        return this.value > other.value;
    }


    public ProgressValue weighted(long factor) {
        return ProgressValue.valueOf(this.value * factor);
    }

    public ProgressValue weighted(double factor) {
        var result = Math.round(this.value * factor);
        return ProgressValue.valueOf(result);
    }

    public ProgressValue difference(ProgressValue value) {
        return ProgressValue.valueOf(this.value - value.value);
    }

    public ProgressValue add(ProgressValue delta) {
        return ProgressValue.valueOf(this.value + delta.value);
    }
}

