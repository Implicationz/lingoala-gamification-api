package com.lingosphinx.gamification.jpa;

import com.lingosphinx.gamification.domain.ExperienceValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExperienceValueConverter implements AttributeConverter<ExperienceValue, Long> {
    @Override
    public Long convertToDatabaseColumn(ExperienceValue attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public ExperienceValue convertToEntityAttribute(Long dbData) {
        return dbData != null ? ExperienceValue.valueOf(dbData) : null;
    }
}