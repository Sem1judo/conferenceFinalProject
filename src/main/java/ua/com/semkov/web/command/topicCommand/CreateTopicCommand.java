package ua.com.semkov.web.command.topicCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;
import ua.com.semkov.web.validation.TopicValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTopicCommand extends Command {
    private static final long serialVersionUID = 1863978254019582513L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(CreateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String userId = request.getParameter("user_id");
        String eventId = request.getParameter("event_id");


        ArrayList<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(description);
        fields.add(userId);
        fields.add(eventId);

        String errorMessage;
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = "All fields are required to be filled";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
            }
        }

        Topic topic = new Topic(name, description, Long.valueOf(userId), Long.valueOf(eventId));

        if (TopicValidation.isValidTopic(topic)) {
            try {
                topicService.createTopic(topic);
            } catch (ServiceException e) {
                errorMessage = "Topic wasn't created";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage, e);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
            }
        } else {
            errorMessage = "Topic is not valid";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE;
        }

        log.trace("created topic --> " + topic);

        log.debug("Commands finished");

        return Path.REDIRECT + Path.COMMAND__LIST_TOPICS;
    }
}
