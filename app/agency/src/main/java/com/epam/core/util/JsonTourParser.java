package com.epam.core.util;

import com.epam.core.entity.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The type Json tour parser.
 */
public class JsonTourParser {
    private static final Logger LOGGER = LogManager.getLogger(JsonTourParser.class);

    /**
     * Parse list.
     *
     * @param file the file
     * @return the list
     */
    public static List<Tour> parse(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Tour[] tour = mapper.readValue(file, Tour[].class);
            return Arrays.asList(tour);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return null;
    }


}
