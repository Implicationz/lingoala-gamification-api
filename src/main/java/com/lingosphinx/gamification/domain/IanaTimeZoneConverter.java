package com.lingosphinx.gamification.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IanaTimeZoneConverter implements AttributeConverter<IanaTimeZone, String> {

    @Override
    public String convertToDatabaseColumn(IanaTimeZone attribute) {
        return (attribute == null) ? null : attribute.getId();
    }

    @Override
    public IanaTimeZone convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : IanaTimeZone.of(dbData);
    }
}
