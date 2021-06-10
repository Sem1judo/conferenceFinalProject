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

        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag((String) session.getAttribute("defaultLocale"));
        ResourceBundle labels = ResourceBundle.getBundle("resources", locale);


        Topic topic;
        TopicDto topicDto;
        String id = request.getParameter("id");
        if (id == null) {
            id = (String) session.getAttribute("id");
        }

        try {
            topic = topicService.getTopicById(Long.valueOf(id));
        } catch (ServiceException e) {
            log.error(labels.getString("error_404_topic-get"), e);
            session.setAttribute(ERROR_MESSAGE, labels.getString("error_404_topic-get"));
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }
        log.trace("chosen topic ---> " + topic);

        boolean isUpdate;
        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (isUpdate) {
            String REDIRECT = getPathIfValidTopic(request, session, labels, topic);
            if (REDIRECT != null) return REDIRECT;
        } else {
            try {
                topicDto = topicService.getTopicDtoById(topic.getId());
            } catch (ServiceException e) {
                session.setAttribute(ERROR_MESSAGE, labels.getString("error_404_topic-notValid"));
                log.error(ERROR_MESSAGE + " --> " + labels.getString("error_404_topic-notValid"));
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
            session.setAttribute("topicDto", topicDto);
            session.setAttribute("topic", topic);
        }


        log.debug("Commands UpdateTopicCommand finished");
        return Path.REDIRECT + Path.PAGE__TOPIC_EDIT;
    }

    private String getPathIfValidTopic(HttpServletRequest request, HttpSession session, ResourceBundle labels, Topic topic) {
        String errorMessage;
        TopicDto topicDto;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String userId = request.getParameter("speakerId");
        String eventId = request.getParameter("eventId");
        String isConfirm = request.getParameter("isConfirm");

        ArrayList<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(description);
        fields.add(userId);
        fields.add(eventId);

        fields.add(isConfirm);

        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                errorMessage = labels.getString("error_404_fields");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        }

        topic.setName(name);
        topic.setDescription(description);
        topic.setEventId(Long.valueOf(eventId));
        topic.setUserId(Long.valueOf(userId));
        topic.setConfirm(Boolean.parseBoolean(isConfirm));

        if (TopicValidation.isValidTopic(topic)) {
            try {
                topicService.updateTopic(topic);
            } catch (ServiceException e) {
                errorMessage = labels.getString("error_404_topic-update");
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(ERROR_MESSAGE + " --> " + errorMessage, e);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }
        } else {
            errorMessage = labels.getString("error_404_topic-notValid");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        try {
            topicDto = topicService.getTopicDtoById(topic.getId());
        } catch (ServiceException e) {
            errorMessage = labels.getString("error_404_topic-notValid");
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(ERROR_MESSAGE + " --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }

        session.setAttribute("topicDto", topicDto);
        session.setAttribute("topic", topic);

        return null;
    }


}
