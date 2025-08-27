package com.lingosphinx.gamification.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lingosphinx.gamification.domain.ExperienceValue;

import java.io.IOException;

public class ExperienceValueDeserializer extends JsonDeserializer<ExperienceValue> {
    @Override
    public ExperienceValue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ExperienceValue.valueOf(p.getValueAsLong());
    }
}
