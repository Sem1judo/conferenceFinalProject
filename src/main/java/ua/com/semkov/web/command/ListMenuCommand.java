package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Lists menu items.
 * 
 * @author D.Kolesnikov
 * 
 */
public class ListMenuCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(ListMenuCommand.class);

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