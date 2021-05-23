package ua.com.semkov.web.command.eventCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateEventCommand extends Command {
    private static final long serialVersionUID = 1863978254519586513L;

    private EventServiceImpl eventService = new EventServiceImpl();

    private static final Logger log = Logger.getLogger(CreateEventCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        String forward = Path.COMMAND_LIST_EVENTS;

        HttpSession session = request.getSession();

        String title = request.getParameter("title");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        String organizedId = request.getParameter("organizer_id");

        // if (isValid()){}


        Event event = new Event.Builder(title,
                LocalDateTime.parse(start_time),
                LocalDateTime.parse(end_time),
                Long.valueOf(organizedId)).
                location(location).
                description(description)
                .build();


        log.trace("obtained event --> " + event);

        try {
            eventService.createEvent(event);
        } catch (ServiceException e) {
            log.error("can't create event", e);
        }

        log.debug("Commands finished");

        return forward;
    }
}
