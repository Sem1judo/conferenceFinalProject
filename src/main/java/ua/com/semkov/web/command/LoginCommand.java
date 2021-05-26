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

/**
 * Login command.
 *
 * @author S.Semkov
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        // error handler
        String errorMessage;
        String forward = Path.PAGE__ERROR_PAGE;


        if (userService.isEmpty(login, password)) {
            errorMessage = "Login or password cannot be empty";
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
        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login and password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;

        } else {
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.MODERATOR)
                forward = Path.COMMAND_LIST_EVENTS;

            if (userRole == Role.CLIENT)
                forward = Path.COMMAND__LIST_MENU;

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