package ua.com.semkov.web.command.adminCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminConfirmTopicCommand extends Command {
    private static final long serialVersionUID = 1863972354019582513L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(AdminConfirmTopicCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command AdminConfirmTopicCommand starts");

        HttpSession session = request.getSession();

        String id = request.getParameter("id");

        Topic topic;
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

        topic.setConfirm(Boolean.TRUE);
        try {
            topicService.updateTopic(topic);
        } catch (ServiceException e) {
            log.error("can't update topic", e);
            errorMessage = "Topic wasn't confirmed";
            session.setAttribute("errorMessage", errorMessage);
            return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
        }


        log.trace("updated topic -- >" + topic);


        log.debug("Commands UpdateTopicCommand finished");
        return Path.REDIRECT + Path.COMMAND_ADMIN_LIST_TOPICS;
    }


}
