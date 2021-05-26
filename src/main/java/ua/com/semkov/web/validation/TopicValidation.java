package ua.com.semkov.web.validation;

import ua.com.semkov.db.entity.Topic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopicValidation {

    private final static String REGEX_NAME = "^\\w{3,255}$";
    private final static String REGEX_ID = "^\\d*$";
    private final static String REGEX_DESCRIPTION = "^\\w{8,1024}$";


    public static boolean isValidTopic(Topic topic) {
        return
                isValidName(topic.getName(), REGEX_NAME) &&
                        isValidDescription(topic.getDescription(), REGEX_DESCRIPTION) &&
                        isValidId(topic.getUserId().toString(), REGEX_ID) &&
                        isValidId(topic.getEventId().toString(), REGEX_ID);

    }


    private static boolean isValidName(String name, String regexName) {
        Pattern p = Pattern.compile(regexName);

        if (name == null || name.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(name);

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

    private static boolean isValidId(String id, String regexId) {
        Pattern p = Pattern.compile(regexId);

        if (id == null || id.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(id);

        return m.matches();
    }

}
