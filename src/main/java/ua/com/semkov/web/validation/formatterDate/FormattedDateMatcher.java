package ua.com.semkov.web.validation.formatterDate;

import java.util.regex.Pattern;

public class FormattedDateMatcher implements DateMatcher {

    private static Pattern DATE_PATTERN = Pattern.compile(
            "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");

    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
