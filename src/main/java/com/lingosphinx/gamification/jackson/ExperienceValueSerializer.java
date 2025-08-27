package com.lingosphinx.gamification.jackson;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lingosphinx.gamification.domain.ExperienceValue;

import java.io.IOException;

public class ExperienceValueSerializer extends JsonSerializer<ExperienceValue> {
    @Override
    public void serialize(ExperienceValue value, com.fasterxml.jackson.core.JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }
}
