package ua.com.semkov.web.command.adminCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminConfirmListCommand extends Command {
    private static final long serialVersionUID = 1863972354019582513L;

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    private static final Logger log = Logger.getLogger(AdminConfirmListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command AdminConfirmTopicCommand starts");

        List<TopicDto> topicDtos = null;

        try {
            topicDtos = topicService.getTopicsDto();
        } catch (ServiceException e) {
            log.error("Problem getting topics DTO", e);
        }

        request.setAttribute("topics", topicDtos);

        log.debug("Commands finished");

        return Path.PAGE__ADMIN_CONFIRM;
    }
}
