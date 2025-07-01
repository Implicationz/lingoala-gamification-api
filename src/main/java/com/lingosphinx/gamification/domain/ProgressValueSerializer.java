package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class ProgressValueSerializer extends JsonSerializer<ProgressValue> {
    @Override
    public void serialize(ProgressValue value, com.fasterxml.jackson.core.JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }
}
