package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.UserServiceImpl;
import ua.com.semkov.web.validation.UserValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Update settings items.
 *
 * @author S.Semkov
 */
public class UpdateSettingsCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;
    private static final Logger log = Logger.getLogger(UpdateSettingsCommand.class);
    private static final String ERROR_MESSAGE = "errorMessage";

    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);

        String id = request.getParameter("id");
        if (id == null) {
            id = (String) session.getAttribute("id");
        }

        boolean isUpdate;

        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (isUpdate) {
            String REDIRECT = getPathIfValidUser(request, session, labels, Long.valueOf(id));
            if (REDIRECT != null) return REDIRECT;

        }


        log.debug("Command finished");
        return Path.REDIRECT + Path.PAGE__SETTINGS;
    }

    private String getPathIfValidUser(HttpServletRequest request, HttpSession session, ResourceBundle labels, Long id) {
        String errorMessage;
        User user;

        try {
            user = userService.getUserById(id);
        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_auth-notExist");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " -- > " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }


        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");


        ArrayList<String> fields = new ArrayList<>();
        fields.add(firstName);
        fields.add(lastName);
        fields.add(email);
        fields.add(login);

        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = labels.getString("error_404_fields");
                log.error(ERROR_MESSAGE + " --> " + errorMessage);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }

        user = new User.Builder(Optional.of(login).orElse(user.getLogin()),
                user.getPassword(),
                Optional.of(email).orElse(user.getEmail()),
                user.getPhone())
                .firstName(firstName)
                .lastName(lastName)
                .id(user.getId())
                .topics(user.getTopics())
                .registrationDate(user.getRegistrationDate())
                .roleId(user.getRoleId())
                .localeName(user.getLocaleName())
                .build();

        log.trace(user);


        if (UserValidation.isValidUser(user)) {
            try {
                userService.updateUser(user);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_user-update");
                log.error(errorMessage, e);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        } else {
            errorMessage = labels.getString("error_404_user-notValid");
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        boolean isPasswordUpdate = false;
        String password = request.getParameter("password");
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
            isPasswordUpdate = true;
        }


        if (isPasswordUpdate) {
            try {
                userService.updateUserPassword(user.getId(), user.getPassword(), password);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_user-update");
                log.error(errorMessage, e);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }

        log.trace("Updated user = " + user);
        session.setAttribute("user", user);

        return null;
    }

}