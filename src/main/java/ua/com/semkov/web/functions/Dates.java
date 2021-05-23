package ua.com.semkov.web.functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Dates {
    private Dates() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
