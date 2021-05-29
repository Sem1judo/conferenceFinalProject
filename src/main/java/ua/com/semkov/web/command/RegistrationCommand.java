package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.db.entity.Role;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.service.impl.UserServiceImpl;
import ua.com.semkov.Path;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.web.validation.UserValidation;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Login command.
 *
 * @author S.Semkov
 */
public class RegistrationCommand extends Command {

    private static final long serialVersionUID = -3071531593627692473L;

    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command registration starts");

        HttpSession session = request.getSession();

        // obtain data from the request
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");

        ArrayList<String> fields = new ArrayList<>(6);
        fields.add(password);
        fields.add(email);
        fields.add(phone);
        fields.add(firstName);
        fields.add(lastName);
        fields.add(login);


        // error handler
        String errorMessage;


        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = "All fields are required to be filled";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }


        User user = new User.Builder(login, password, email, phone)
                .firstName(firstName)
                .lastName(lastName).build();


        if (UserValidation.isValidUser(user)) {
            try {
                userService.registration(user);
                log.trace("Inserted user : user --> " + user);
            } catch (ServiceException e) {
                log.error("Problem with registration user", e);
            }

        } else {
            errorMessage = "User not valid";
            log.error("errorMessage --> " + errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        Role userRole = Role.getRole(user);
        log.trace("userRole --> " + userRole);

        setUserSessionAndCookies(response, session, user, userRole, log);

        log.info("User " + user + " register and logged as " + userRole.toString().toLowerCase());

        // work with i18n
        setUserLocale(session, user, log);

        log.debug("Command registration finished");

        return Path.REDIRECT + Path.PAGE__PROFILE_USER;
    }

    static void setUserLocale(HttpSession session, User user, Logger log) {
        String userLocaleName = user.getLocaleName();
        log.trace("userLocalName --> " + userLocaleName);

        if (userLocaleName != null && !userLocaleName.isEmpty()) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

            session.setAttribute("defaultLocale", userLocaleName);
            log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

            log.info("Locale for user: defaultLocale --> " + userLocaleName);
        }
    }

    static void setUserSessionAndCookies(HttpServletResponse response, HttpSession session, User user, Role userRole, Logger log) {
        Cookie ck = new Cookie("user", user.getLogin());
        ck.setMaxAge(24 * 60);
        response.addCookie(ck);

        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);
    }


}

