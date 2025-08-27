package com.lingosphinx.gamification.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lingosphinx.gamification.domain.ProgressValue;

import java.io.IOException;

public class ProgressValueDeserializer extends JsonDeserializer<ProgressValue> {
    @Override
    public ProgressValue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ProgressValue.valueOf(p.getValueAsLong());
    }
}
