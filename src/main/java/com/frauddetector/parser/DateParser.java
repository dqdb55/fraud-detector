package com.frauddetector.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The class for parsing timestamp and checking if given date (timestamp) has valid format.
 */
public class DateParser {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(DateParser.class);

    private DateParser() {
    }

    /**
     * Parses given timestamp to {@link LocalDate}.
     *
     * @param timestamp timestamp
     * @return LocalDate
     */
    public static LocalDate parseDate(String timestamp) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(timestamp, formatter);
        } catch (DateTimeParseException exc) {
            logger.warn("DateTimeParseException: {}", timestamp);
            return null;
        }
    }

    /**
     * Checks if given timestamp has valid format.
     *
     * @param strDate timestamp
     * @return true if given timestamp has valid format, otherwise - false.
     */
    public static boolean isDateValid(String strDate) {
        return isValidFormat(DATE_FORMAT, strDate);
    }

    private static boolean isValidFormat(String format, String timestamp) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate.parse(timestamp, formatter);
            return true;
        } catch (DateTimeParseException exc) {
            logger.warn("DateTimeParseException: {}", timestamp);
            return false;
        }
    }
}
