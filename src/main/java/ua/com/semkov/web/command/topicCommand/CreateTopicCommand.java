package ua.com.semkov.web.command.topicCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateTopicCommand extends Command {
    private static final long serialVersionUID = 1863978254019582513L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(CreateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");

        String forward = Path.COMMAND__LIST_TOPICS;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String userId = request.getParameter("user_id");
        String eventId = request.getParameter("event_id");


        // if (isValid()){}


        Topic topic = new Topic(name, description, Long.valueOf(userId), Long.valueOf(eventId));


        log.trace("obtained topic --> " + topic);

        try {
            topicService.createTopic(topic);
        } catch (ServiceException e) {
            log.error("can't create topic", e);
        }

        log.debug("Commands finished");

        return forward;
    }
}
