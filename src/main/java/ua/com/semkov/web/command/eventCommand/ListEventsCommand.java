package ua.com.semkov.web.command.eventCommand;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.web.command.Command;


/**
 * Lists events.
 *
 * @author S.Semkov
 */
public class ListEventsCommand extends Command {

    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger log = Logger.getLogger(ListEventsCommand.class);

    private final EventServiceImpl eventService = new EventServiceImpl();


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Commands starts");


        int page = 1;
        int recordsPerPage = 2;


        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Event> events = null;

        try {
            events = eventService.getEventsPagination((page - 1) * recordsPerPage,
                    recordsPerPage);
        } catch (ServiceException daoException) {
            daoException.printStackTrace();
        }

        int noOfRecords = eventService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        log.trace("Set the request attribute: events --> " + events);
        log.trace("Set the request attribute: noOfPages(total number of events) --> " + noOfPages);
        log.trace("Set the request attribute founded events: --> " + events);

        request.setAttribute("events", events);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);


        log.debug("Commands finished");

        return Path.PAGE__LIST_EVENTS;
    }

}