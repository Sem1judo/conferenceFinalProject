package ua.com.semkov.web.command.eventCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEventCommand extends Command {
    private static final long serialVersionUID = 1861978252512286513L;

    private final EventServiceImpl eventService = new EventServiceImpl();

    private static final Logger log = Logger.getLogger(DeleteEventCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        String id = request.getParameter("id");

        log.trace("Event id for removing-- > " + id);
        try {
            eventService.removeEvent(Long.valueOf(id));
        } catch (ServiceException e) {
            log.error("problem with removing event", e);
        }

        log.debug("Commands finished");

        return Path.REDIRECT + Path.PAGE__LIST_EVENTS;
    }
}