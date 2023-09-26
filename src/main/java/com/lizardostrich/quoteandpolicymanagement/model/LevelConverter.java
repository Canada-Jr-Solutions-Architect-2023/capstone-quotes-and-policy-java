package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LevelConverter implements AttributeConverter<Level, String> {
    @Override
    public String convertToDatabaseColumn(Level level){
        return level.toString();
    }

    @Override
    public Level convertToEntityAttribute(String levelString){
        return Level.valueOf(levelString);
    }
}
