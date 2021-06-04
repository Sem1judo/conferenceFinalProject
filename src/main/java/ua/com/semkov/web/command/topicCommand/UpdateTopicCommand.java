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

public class UpdateTopicCommand extends Command {
    private static final long serialVersionUID = 3331975151519536313L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(UpdateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command UpdateTopicCommand starts");

        boolean isUpdate;

        HttpSession session = request.getSession();

        String id = request.getParameter("id");

        Topic topic;
        TopicDto topicDto;
        String errorMessage;

        try {
            topic = topicService.getTopicById(Long.valueOf(id));
        } catch (ServiceException e) {
            log.error("can't get topic by id ", e);
            errorMessage = "Can't get topic";
            session.setAttribute("errorMessage", errorMessage);
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


            ArrayList<String> fields = new ArrayList<>();
            fields.add(name);
            fields.add(description);
            fields.add(userId);
            fields.add(eventId);
            fields.add(isConfirm);


            for (String field : fields) {
                if (field == null || field.isEmpty()) {
                    errorMessage = "All fields are required to be filled";
                    session.setAttribute("errorMessage", errorMessage);
                    log.error("errorMessage --> " + errorMessage);
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
                    errorMessage = "Topic wasn't updated";
                    session.setAttribute("errorMessage", errorMessage);
                    log.error("errorMessage --> " + errorMessage, e);
                    return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
                }
            } else {
                errorMessage = "Topic is not valid";
                session.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }

        }
        try {
            topicDto = topicService.getTopicDtoById(topic.getId());
        } catch (ServiceException e) {
            errorMessage = "TopicDto can't be loaded";
            session.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }


        log.trace("updated topic -- >" + topic);
        log.trace("set attribute topic DTO -- >" + topicDto);


        session.setAttribute("topicDto", topicDto);
        session.setAttribute("topic", topic);


        log.debug("Commands UpdateTopicCommand finished");
        return Path.REDIRECT + Path.PAGE__TOPIC_EDIT;
    }
}