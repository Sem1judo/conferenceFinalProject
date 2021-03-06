package ua.com.semkov.web.command.topicCommand;

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

/**
 * Lists orders.
 *
 * @author D.Kolesnikov
 */
public class ListTopicsCommand extends Command {

    private static final long serialVersionUID = 1863978254601586513L;

    private static final Logger log = Logger.getLogger(ListTopicsCommand.class);


    private final TopicServiceImpl topicService = new TopicServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Commands starts");

        int page = 1;
        int recordsPerPage = 5;
        List<TopicDto> topicDtos = null;

        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        try {
            topicDtos = topicService.getTopicsDtoPaginationConfirmed((page - 1) * recordsPerPage,
                    recordsPerPage);
        } catch (ServiceException e) {
            log.error("Problem getting topics DTO", e);
        }

        int noOfRecords = topicService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        log.trace("Set the request attribute: topics --> " + topicDtos);
        log.trace("Set the request attribute: noOfPages(total number of topics) --> " + noOfPages);

        request.setAttribute("topics", topicDtos);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        log.debug("Commands finished");

        return Path.PAGE__LIST_TOPICS;
    }

}