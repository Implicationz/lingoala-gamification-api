package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class IanaTimeZoneSerializer extends JsonSerializer<IanaTimeZone> {
    @Override
    public void serialize(IanaTimeZone value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getId());
    }
}

