package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.Status;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.UserServiceImpl;
import ua.com.semkov.web.validation.EventValidation;
import ua.com.semkov.web.validation.UserValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Update settings items.
 *
 * @author S.Semkov
 */
public class UpdateSettingsCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(UpdateSettingsCommand.class);
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        if (id == null) {
            id = (String) session.getAttribute("id");
        }

        User user;
        String errorMessage;
        boolean isUpdate;


        try {
            user = userService.getUserById(Long.valueOf(id));
        } catch (ServiceException e) {
            errorMessage = "User doesn't exist in db";
            session.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }
// LOCALE
        String localeToSet = request.getParameter("localeToSet");
        if (localeToSet != null && !localeToSet.isEmpty()) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
            session.setAttribute("defaultLocale", localeToSet);
            user.setLocaleName(localeToSet);
        }

        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (user != null && isUpdate) {

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
                    errorMessage = "All fields are required to be filled";
                    session.setAttribute("errorMessage", errorMessage);
                    log.error("errorMessage --> " + errorMessage);
                    return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
                }
            }

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLogin(login);

            if (UserValidation.isValidUser(user)) {
                try {
                    userService.updateUser(user);

                } catch (ServiceException e) {
                    log.error("Can't update user", e);
                    errorMessage = "Can't update user";
                    session.setAttribute("errorMessage", errorMessage);
                    return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
                }
            } else {
                errorMessage = "User is not valid";
                session.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }

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
                log.error("can't update password", e);
                errorMessage = "Can't change password ";
                session.setAttribute("errorMessage", errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }

        log.trace("Updated user = " + user);

        session.setAttribute("user", user);

        log.debug("Command finished");
        return Path.REDIRECT + Path.PAGE__SETTINGS;
    }

}