package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class IanaTimeZoneDeserializer extends JsonDeserializer<IanaTimeZone> {
    @Override
    public IanaTimeZone deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return IanaTimeZone.of(p.getValueAsString());
    }
}
