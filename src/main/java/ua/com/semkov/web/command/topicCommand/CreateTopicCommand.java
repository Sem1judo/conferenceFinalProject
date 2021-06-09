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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateTopicCommand extends Command {
    private static final long serialVersionUID = 1863978254019582513L;
    private static final String ERROR_MESSAGE = "errorMessage";

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(CreateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String userId = request.getParameter("user_id");
        String eventId = request.getParameter("event_id");

        Topic topic = getTopic(session, labels, name, description, userId, eventId);

        if (topic == null) return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;

        log.trace("created topic --> " + topic);

        log.debug("Commands finished");

        return Path.REDIRECT + Path.COMMAND__LIST_TOPICS;
    }

    private Topic getTopic(HttpSession session, ResourceBundle labels, String name, String description, String userId, String eventId) {
        String errorMessage;
        ArrayList<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(description);
        fields.add(eventId);
        fields.add(userId);


        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = labels.getString("error_404_fields");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage);
                return null;
            }
        }

        Topic topic = new Topic(name, description, Long.valueOf(userId),
                Long.valueOf(eventId));

        if (TopicValidation.isValidTopic(topic)) {
            try {
                topicService.createTopic(topic);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_topic-creation");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage);
                return null;
            }
        } else {
            errorMessage = labels.getString("error_404_topic-notValid");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return null;
        }
        return topic;
    }
}
