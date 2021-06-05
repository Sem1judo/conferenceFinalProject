package ua.com.semkov.web.validation;

import ua.com.semkov.db.entity.Event;
import ua.com.semkov.web.validation.formatterDate.FormattedDateMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventValidation {

    private final static String REGEX_TITLE = "^[\\w\\s\\.\\,\\-\\'\\!\\?\\+\\#\\*А-Яа-яЪъЇї]{3,255}$";
    private final static String REGEX_ORGANIZER_ID = "^\\d*$";
    private final static String REGEX_DESCRIPTION = "^[\\w\\s\\.\\,\\-\\'\\!\\?\\+\\#\\*А-Яа-яЪъЇї]{8,1024}$";
    private final static String REGEX_LOCATION = "^[\\w\\s\\,\\.\\-]{3,255}$";


    public static boolean isValidEvent(Event event) {
        return
                isValidTitle(event.getTitle(), REGEX_TITLE) &&
                        isValidDescription(event.getDescription(), REGEX_DESCRIPTION) &&
                        isValidLocation(event.getLocation(), REGEX_LOCATION) &&
                        isValidOrganizerId(event.getOrganizerId().toString(), REGEX_ORGANIZER_ID);
    }


    private static boolean isValidTitle(String title, String regexTitle) {
        Pattern p = Pattern.compile(regexTitle);

        if (title == null || title.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(title);

        return m.matches();
    }

    private static boolean isValidDescription(String description, String regexDescription) {
        Pattern p = Pattern.compile(regexDescription);

        if (description == null || description.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(description);

        return m.matches();
    }

    private static boolean isValidLocation(String location, String regexLocation) {
        Pattern p = Pattern.compile(regexLocation);

        if (location == null || location.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(location);

        return m.matches();
    }

    private static boolean isValidStartTime(String startTime) {
        FormattedDateMatcher fDate = new FormattedDateMatcher();

        if (startTime == null || startTime.isEmpty()) {
            return false;
        }

        return fDate.matches(startTime);
    }

    private static boolean isValidEndTime(String endTime) {
        FormattedDateMatcher fDate = new FormattedDateMatcher();

        if (endTime == null || endTime.isEmpty()) {
            return false;
        }

        return fDate.matches(endTime);
    }

    private static boolean isValidOrganizerId(String organizer_id, String regexOrganizerId) {
        Pattern p = Pattern.compile(regexOrganizerId);

        if (organizer_id == null || organizer_id.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(organizer_id);

        return m.matches();
    }
}
