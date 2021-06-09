package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.db.entity.Role;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.service.impl.UserServiceImpl;
import ua.com.semkov.Path;
import ua.com.semkov.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login command.
 *
 * @author S.Semkov
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();
        ResourceBundle labels = ResourceBundle.getBundle("resources", request.getLocale());

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // error handler
        String errorMessage;
        String forward = Path.PAGE__ERROR_PAGE_404;


        if (userService.isEmpty(login, password)) {
            errorMessage = labels.getString("error_404_auth-empty");
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = null;
        try {
            user = userService.getUser(login);
        } catch (ServiceException e) {
            log.error("problem with getting user by login", e);
        }


        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = labels.getString("error_404_auth-notExist");
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;

        } else {
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.MODERATOR)
                forward = Path.COMMAND_LIST_EVENTS;

            if (userRole == Role.CLIENT)
                forward = Path.COMMAND__PROFILE_LIST_EVENTS;

            if (userRole == Role.SPEAKER)
                forward = Path.COMMAND__PROFILE_LIST_EVENTS;

            RegistrationCommand.setUserSessionAndCookies(response, session, user, userRole, log);

            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

            // work with i18n
            RegistrationCommand.setUserLocale(session, user, log);
        }


        log.debug("Command finished");
        return forward;
    }

}