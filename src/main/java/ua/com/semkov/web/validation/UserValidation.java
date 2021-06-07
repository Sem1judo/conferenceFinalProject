package ua.com.semkov.web.validation;

import ua.com.semkov.db.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    private final static String REGEX_LOGIN = "^\\w{3,16}$";
    private final static String REGEX_NAME = "^[A-Za-zА-Яа-яЁё]{2,45}$";
    private final static String REGEX_PHONE = "(?:\\+38)?(?:\\(0\\d{2}\\)[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|044[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|044[0-9]{7})$";
    private final static String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{4,32}$";
    private final static String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isValidUser(User user) {
        return isValidEmail(user.getEmail(), REGEX_EMAIL) &&
                isValidFirstName(user.getFirstName(), REGEX_NAME) &&
                isValidLastName(user.getLastName(), REGEX_NAME) &&
                isValidLogin(user.getLogin(), REGEX_LOGIN);
    }


    private static boolean isValidLogin(String login, String regexLogin) {
        Pattern p = Pattern.compile(regexLogin);

        if (login == null || login.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(login);

        return m.matches();
    }

    private static boolean isValidEmail(String email, String regexEmail) {
        Pattern p = Pattern.compile(regexEmail);

        if (email == null || email.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(email);

        return m.matches();
    }

    private static boolean isValidPassword(String password, String regexPassword) {
        Pattern p = Pattern.compile(regexPassword);

        if (password == null || password.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(password);

        return m.matches();
    }

    private static boolean isValidFirstName(String firstName, String regexFirstName) {
        Pattern p = Pattern.compile(regexFirstName);

        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(firstName);

        return m.matches();
    }

    private static boolean isValidLastName(String lastName, String regexLastName) {
        Pattern p = Pattern.compile(regexLastName);

        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(lastName);

        return m.matches();
    }

    private static boolean isValidPhone(String lastName, String regexPhone) {
        Pattern p = Pattern.compile(regexPhone);

        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(regexPhone);

        return m.matches();
    }
}
