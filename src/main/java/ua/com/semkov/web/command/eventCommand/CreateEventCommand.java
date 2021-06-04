package ua.com.semkov.web.command.eventCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.web.command.Command;
import ua.com.semkov.web.validation.EventValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateEventCommand extends Command {
    private static final long serialVersionUID = 1863978254519586513L;

    private EventServiceImpl eventService = new EventServiceImpl();

    private static final Logger log = Logger.getLogger(CreateEventCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        String title = request.getParameter("title");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        String organizedId = request.getParameter("organizer_id");

        ArrayList<String> fields = new ArrayList<>();
        fields.add(title);
        fields.add(location);
        fields.add(description);
        fields.add(start_time);
        fields.add(end_time);
        fields.add(organizedId);

        // error handler
        String errorMessage;

        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = "All fields are required to be filled";
                session.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }


        Event event = new Event.Builder(title,
                LocalDateTime.parse(start_time),
                LocalDateTime.parse(end_time),
                Long.valueOf(organizedId)).
                location(location).
                description(description)
                .build();

        log.trace("obtained event --> " + event);

        if (EventValidation.isValidEvent(event)) {
        try {
            eventService.createEvent(event);
        } catch (ServiceException e) {
          errorMessage = "Can't create event";
            session.setAttribute("errorMessage", errorMessage);
          log.error("errorMessage --> " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
          } else {
            errorMessage = "Event  is not valid";
            session.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        log.debug("Commands finished");

        return Path.REDIRECT + Path.COMMAND_LIST_EVENTS;

    }
}
