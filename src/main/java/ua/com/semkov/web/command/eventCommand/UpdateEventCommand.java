package ua.com.semkov.web.command.eventCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.Status;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateEventCommand extends Command {
    private static final long serialVersionUID = 3331978252519586513L;
    private static final Logger log = Logger.getLogger(UpdateEventCommand.class);
    private static final String ERROR_MESSAGE = "errorMessage";

    private final EventServiceImpl eventService = new EventServiceImpl();
    private final TopicServiceImpl topicService = new TopicServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);


        String id = request.getParameter("id");
        if (id == null) {
            id = (String) session.getAttribute("id");
        }

        boolean isUpdate;
        Event event;

        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (isUpdate) {
            String REDIRECT = getPathIfValidEvent(request, session, labels, Long.valueOf(id));
            if (REDIRECT != null) return REDIRECT;
        } else {
            try {
                event = eventService.getEvent(Long.valueOf(id));
                Status eventStatus = Status.getStatusStatic(event);
                List<TopicDto> eventTopics = topicService.getTopicsDtoByEvent(event.getId());

                session.setAttribute("eventStatus", eventStatus);
                session.setAttribute("eventTopics", eventTopics);
                session.setAttribute("event", event);

            } catch (ServiceException e) {
                session.setAttribute(ERROR_MESSAGE, labels.getString("error_404_event-get"));
                log.error(ERROR_MESSAGE + " --> " + labels.getString("error_404_event-get"), e);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }

        }


        log.debug("Commands finished");

        return Path.REDIRECT + Path.PAGE__EVENT_EDIT;
    }

    private String getPathIfValidEvent(HttpServletRequest request, HttpSession session, ResourceBundle labels, Long id) {

        Event event;
        Status eventStatus;
        List<TopicDto> eventTopics;

        String errorMessage;

        try {
            event = eventService.getEvent(id);
        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_event-get");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage, e);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        String title = request.getParameter("title");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        String organizedId = request.getParameter("organizer_id");
        String statusId = request.getParameter("status_id");

        ArrayList<String> fields = new ArrayList<>();
        fields.add(title);
        fields.add(location);
        fields.add(description);
        fields.add(start_time);
        fields.add(end_time);
        fields.add(organizedId);


        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = labels.getString("error_404_fields");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }

        event = new Event.Builder(title, LocalDateTime.parse(start_time),
                LocalDateTime.parse(end_time),
                Long.valueOf(organizedId))
                .location(location)
                .description(description)
                .topics(event.getTopics())
                .id(event.getId())
                .users(event.getUsers())
                .statusId(Long.valueOf(statusId)).build();


        if (EventValidation.isValidEvent(event)) {
            try {
                eventService.updateEvent(event);
                log.trace("updated event -- >" + event);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_event-update");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage, e);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        } else {
            errorMessage = labels.getString("error_404_event-notValid");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        try {
            eventStatus = Status.getStatusStatic(event);
            eventTopics = topicService.getTopicsDtoByEvent(event.getId());

        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_event-topicsNotLoaded");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        log.trace("Set the session attribute: eventStatus --> " + eventStatus);

        session.setAttribute("eventStatus", eventStatus);
        session.setAttribute("eventTopics", eventTopics);
        session.setAttribute("event", event);

        return null;
    }
}