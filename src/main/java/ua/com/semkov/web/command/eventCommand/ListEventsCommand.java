package ua.com.semkov.web.command.eventCommand;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Status;
import ua.com.semkov.db.entity.User;
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

        log.debug("Commands ListEventsCommand starts");


        int page = 1;
        int recordsPerPage = 5;

        String sort = "byStartTime";


        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        if (request.getParameter("sort") != null) {
            sort = request.getParameter("sort");
        }

        List<Event> events = null;
        try {
            events = eventService.getEventsPagination((page - 1) * recordsPerPage,
                    recordsPerPage);

        } catch (ServiceException daoException) {
            daoException.printStackTrace();
        }

        int noOfRecords = eventService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        log.trace("Set the request attribute: noOfPages(total number of events) --> " + noOfPages);
        log.trace("Set the request attribute sort: --> " + sort);


        request.setAttribute("events", events);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sort", sort);


        log.debug("Commands ListEventsCommand finished");

        return Path.PAGE__LIST_EVENTS;
    }

}