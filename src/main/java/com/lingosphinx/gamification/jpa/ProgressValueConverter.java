package com.lingosphinx.gamification.jpa;

import com.lingosphinx.gamification.domain.ProgressValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProgressValueConverter implements AttributeConverter<ProgressValue, Long> {
    @Override
    public Long convertToDatabaseColumn(ProgressValue attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public ProgressValue convertToEntityAttribute(Long dbData) {
        return dbData != null ? ProgressValue.valueOf(dbData) : null;
    }
}