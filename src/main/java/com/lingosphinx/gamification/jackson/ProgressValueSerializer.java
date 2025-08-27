package com.lingosphinx.gamification.jackson;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lingosphinx.gamification.domain.ProgressValue;

import java.io.IOException;

public class ProgressValueSerializer extends JsonSerializer<ProgressValue> {
    @Override
    public void serialize(ProgressValue value, com.fasterxml.jackson.core.JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }
}
