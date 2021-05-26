package ua.com.semkov.web.command.eventCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;
import ua.com.semkov.web.validation.EventValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateEventCommand extends Command {
    private static final long serialVersionUID = 3331978252519586513L;

    private static final Logger log = Logger.getLogger(UpdateEventCommand.class);

    private final EventServiceImpl eventService = new EventServiceImpl();
    private final TopicServiceImpl topicService = new TopicServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        boolean isUpdate;

        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        if (id == null) {
            id = (String) session.getAttribute("id");
        }

        Event event;
        List<TopicDto> eventTopics = null;

        String errorMessage;

        try {
            event = eventService.getEvent(Long.valueOf(id));
        } catch (ServiceException e) {
            errorMessage = "Event doesn't exist in db";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
        }

        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (event != null && isUpdate) {

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

            for (String field : fields) {
                if (field == null || field.isEmpty()) {
                    errorMessage = "All fields are required to be filled";
                    request.setAttribute("errorMessage", errorMessage);
                    log.error("errorMessage --> " + errorMessage);
                    return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
                }
            }

            event.setTitle(title);
            event.setLocation(location);
            event.setDescription(description);
            event.setStartTime(LocalDateTime.parse(start_time));
            event.setEndTime(LocalDateTime.parse(end_time));
            event.setOrganizerId(Long.valueOf(organizedId));

            if (EventValidation.isValidEvent(event)) {
                try {
                    eventService.updateEvent(event);
                    eventTopics = topicService.getTopicsDtoByEvent(event.getId());
                } catch (ServiceException e) {
                    log.error("can't update event", e);
                    errorMessage = "Can't update event";
                    request.setAttribute("errorMessage", errorMessage);
                    return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
                }
            } else {
                errorMessage = "Event is not valid";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
            }

        }

        log.trace("updated event -- >" + event);
        log.trace("Event topics -- >" + eventTopics);

        session.setAttribute("eventTopics", eventTopics);
        session.setAttribute("event", event);


        log.debug("Commands finished");


        return Path.REDIRECT + Path.PAGE__EVENT_EDIT;
    }
}