package com.epam.core.util;

import com.epam.core.entity.TourType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The type Tour type converter.
 */
@Converter(autoApply = true)
public class TourTypeConverter implements AttributeConverter<TourType, String> {
    @Override
    public String convertToDatabaseColumn(TourType tourType) {
        return tourType.toString();
    }

    @Override
    public TourType convertToEntityAttribute(String s) {
        return TourType.fromString(s);
    }
}
