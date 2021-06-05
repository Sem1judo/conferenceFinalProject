package ua.com.semkov.web.command.clientCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.service.impl.UsersEventsServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteJoinedEventCommand extends Command {
    private static final long serialVersionUID = 1861978252522286513L;

    private final UsersEventsServiceImpl usersEventsService = new UsersEventsServiceImpl();

    private static final Logger log = Logger.getLogger(DeleteJoinedEventCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");


        String userId = request.getParameter("userId");
        String eventId = request.getParameter("eventId");


        try {
            usersEventsService.removeJoinedEvent(Long.valueOf(eventId), Long.valueOf(userId));
        } catch (ServiceException e) {
            log.error("problem with removing event from user", e);
        }

        log.debug("Commands finished");

        return Path.REDIRECT + Path.COMMAND__PROFILE_LIST_EVENTS;
    }
}