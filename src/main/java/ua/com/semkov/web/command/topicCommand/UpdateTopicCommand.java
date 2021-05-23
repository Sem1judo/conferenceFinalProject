package ua.com.semkov.web.command.topicCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateTopicCommand extends Command {
    private static final long serialVersionUID = 3331975151519536313L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(UpdateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");


        log.debug("Command starts");

        boolean isUpdate;

        HttpSession session = request.getSession();


        String id = request.getParameter("id");

        Topic topic = null;
        TopicDto topicDto = null;

        try {
            topic = topicService.getTopicById(Long.valueOf(id));

        } catch (ServiceException e) {
            log.error("can't get topic by id ", e);
        }


        String updated = request.getParameter("isUpdated");
        isUpdate = Boolean.parseBoolean(updated);

        if (topic != null && isUpdate) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String userId = request.getParameter("speakerId");
            String eventId = request.getParameter("eventId");

            System.out.println(id + "  " + name + "  " + description + "  " + userId + "  " + eventId);
            topic.setName(name);
            topic.setDescription(description);
            topic.setEventId(Long.valueOf(eventId));
            topic.setUserId(Long.valueOf(userId));

        }
        try {
            topicService.updateTopic(topic);
            topicDto = topicService.getTopicDtoById(topic.getId());
        } catch (ServiceException e) {
            log.error("can't update event", e);
        }

        log.trace("updated topic -- >" + topic);
        log.trace("set attribute topic DTO -- >" + topicDto);


        session.setAttribute("topicDto", topicDto);
        session.setAttribute("topic", topic);


        log.debug("Commands finished");
        return Path.REDIRECT + Path.PAGE__TOPIC_EDIT;
    }
}