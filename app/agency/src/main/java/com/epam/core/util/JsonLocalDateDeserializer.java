package com.epam.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The type Json local date deserializer.
 */
public class JsonLocalDateDeserializer extends StdDeserializer<LocalDate> {
    /**
     * Instantiates a new Json local date deserializer.
     */
    protected JsonLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(jsonParser.readValueAs(String.class));
    }
}
