package ua.com.semkov.web.command.topicCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTopicCommand extends Command {
    private static final long serialVersionUID = 1861978257719586513L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(DeleteTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        log.debug("Command starts");


        String id = request.getParameter("id");

        System.out.println("Topic it -- > " + id);
        try {
            topicService.removeTopic(Long.valueOf(id));
        } catch (ServiceException e) {
            log.error("problem remove topic", e);
        }

        log.debug("Commands finished");

        return Path.COMMAND__LIST_TOPICS;
    }
}