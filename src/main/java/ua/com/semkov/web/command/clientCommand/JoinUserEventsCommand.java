package ua.com.semkov.web.command.clientCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;

import ua.com.semkov.db.entity.Event;

import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;

import ua.com.semkov.service.impl.UsersEventsServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class JoinUserEventsCommand extends Command {
    private static final long serialVersionUID = 3331975151519536313L;
    private static final Logger log = Logger.getLogger(JoinUserEventsCommand.class);

    private final UsersEventsServiceImpl usersEventsService = new UsersEventsServiceImpl();
    private final EventServiceImpl eventService = new EventServiceImpl();



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command JoinUserEventsCommand starts");

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);

        Event event;
        String idEvent = request.getParameter("id");
        User user = (User) (session.getAttribute("user"));

        String errorMessage;
        try {
            event = eventService.getEvent(Long.valueOf(idEvent));
            usersEventsService.setEventsForUser(user, event);

        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_joinUser-event");
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        log.debug("Commands JoinUserEventsCommand finished");

        return Path.REDIRECT + Path.COMMAND__PROFILE_LIST_EVENTS;
    }
}