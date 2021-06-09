package ua.com.semkov.web.command.topicCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
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

public class UpdateTopicCommand extends Command {
    private static final long serialVersionUID = 3331975151519536313L;
    private static final String ERROR_MESSAGE = "errorMessage";

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(UpdateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command UpdateTopicCommand starts");

        boolean isUpdate;

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);


        String errorMessage;
        Topic topic;
        TopicDto topicDto;

        String id = request.getParameter("id");

        try {
            topic = topicService.getTopicById(Long.valueOf(id));
        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_topic-get");
            log.error(errorMessage, e);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }
        log.trace("chosen topic ---> " + topic);


        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (topic != null && isUpdate) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String userId = request.getParameter("speakerId");
            String eventId = request.getParameter("eventId");
            String isConfirm = request.getParameter("isConfirm");


            String REDIRECT = fieldsForTopic(session, labels, topic, name, description, userId, eventId, isConfirm);
            if (REDIRECT != null) return REDIRECT;

        }
        try {
            if (topic != null) {
                topicDto = topicService.getTopicDtoById(topic.getId());
            } else throw new ServiceException();
        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_topic-notValid");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }


        log.trace("updated topic -- >" + topic);
        log.trace("set attribute topic DTO -- >" + topicDto);

        session.setAttribute("topicDto", topicDto);
        session.setAttribute("topic", topic);


        log.debug("Commands UpdateTopicCommand finished");
        return Path.REDIRECT + Path.PAGE__TOPIC_EDIT;
    }

    private String fieldsForTopic(HttpSession session, ResourceBundle labels, Topic topic, String name, String description, String userId, String eventId, String isConfirm) {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(description);
        fields.add(userId);
        fields.add(eventId);

        if (topicFields(session, labels, isConfirm, fields, ERROR_MESSAGE, log))
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;

        topic.setName(name);
        topic.setDescription(description);
        topic.setEventId(Long.valueOf(eventId));
        topic.setUserId(Long.valueOf(userId));
        topic.setConfirm(Boolean.parseBoolean(isConfirm));

        if (getTopic(session, labels, topic, topicService, ERROR_MESSAGE, log))
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        return null;
    }

    public static boolean topicFields(HttpSession session, ResourceBundle labels, String isConfirm, ArrayList<String> fields, String errorMessage2, Logger log) {
        String errorMessage;
        fields.add(isConfirm);

        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = labels.getString("error_404_fields");
                session.setAttribute(errorMessage2, errorMessage);
                log.error(errorMessage2 + " --> " + errorMessage);
                return true;
            }
        }
        return false;
    }

    public static boolean getTopic(HttpSession session, ResourceBundle labels, Topic topic, TopicServiceImpl topicService, String errorMessage2, Logger log) {
        String errorMessage;
        if (TopicValidation.isValidTopic(topic)) {
            try {
                topicService.createTopic(topic);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_topic-creation");
                session.setAttribute(errorMessage2, errorMessage);
                log.error(errorMessage2 + " --> " + errorMessage, e);
                return true;
            }
        } else {
            errorMessage = labels.getString("error_404_topic-notValid");
            session.setAttribute(errorMessage2, errorMessage);
            log.error(errorMessage2 + " --> " + errorMessage);
            return true;
        }
        return false;
    }
}