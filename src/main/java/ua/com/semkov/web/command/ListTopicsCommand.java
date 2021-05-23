package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.service.impl.TopicServiceImpl;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Lists orders.
 *
 * @author D.Kolesnikov
 */
public class ListTopicsCommand extends Command {

    private static final long serialVersionUID = 1863978254601586513L;

    private static final Logger log = Logger.getLogger(ListTopicsCommand.class);
    private static final Comparator<TopicDto> compareById = new CompareById();

    private final TopicServiceImpl topicService = new TopicServiceImpl();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Commands starts");

        int page = 1;
        int recordsPerPage = 2;


        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<TopicDto> topicDtos = null;
        System.out.println(topicDtos);
        try {
            topicDtos = topicService.getTopicsDtoPagination((page - 1) * recordsPerPage,
                    recordsPerPage);

        } catch (ServiceException e) {
            log.error("Problem getting topics DTO", e);
        }


        int noOfRecords = topicService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        log.trace("Set the request attribute: topics --> " + topicDtos);
        log.trace("Set the request attribute: noOfPages(total number of events) --> " + noOfPages);

        request.setAttribute("topics", topicDtos);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);


        log.debug("Commands finished");


        return Path.PAGE__LIST_TOPICS;
    }

    private static class CompareById implements Comparator<TopicDto>, Serializable {
        private static final long serialVersionUID = -2573481565177573183L;

        public int compare(TopicDto o1, TopicDto o2) {
            return o1.getId().compareTo(o2.getId());
        }

    }


}