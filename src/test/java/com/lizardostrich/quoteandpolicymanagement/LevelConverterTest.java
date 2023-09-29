package com.lizardostrich.quoteandpolicymanagement;

import com.lizardostrich.quoteandpolicymanagement.model.Level;
import com.lizardostrich.quoteandpolicymanagement.model.LevelConverter;
import com.lizardostrich.quoteandpolicymanagement.testUtils.PolicyUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelConverterTest {

    @Test
    public void testConvertToDatabaseColumn(){
        Level level = Level.STARTER;
        LevelConverter levelConverter = new LevelConverter();

        String levelString  = levelConverter.convertToDatabaseColumn(level);

        assertEquals("STARTER",levelString);
    }

    @Test
    public void testConvertToEntityAttribute(){
        String levelString = "STARTER";
        LevelConverter levelConverter = new LevelConverter();

        Level level = levelConverter.convertToEntityAttribute(levelString);

        assertEquals(Level.STARTER,level);
    }
}
