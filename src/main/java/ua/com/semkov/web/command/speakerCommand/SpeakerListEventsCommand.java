package ua.com.semkov.web.command.speakerCommand;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.dao.impl.UsersEventsDaoImpl;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.EventServiceImpl;
import ua.com.semkov.service.impl.UsersEventsServiceImpl;
import ua.com.semkov.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * List events conducted by speaker .
 *
 * @author S.Semkov
 */
public class SpeakerListEventsCommand extends Command {

    private static final long serialVersionUID = 1863978251529586513L;

    private static final Logger log = Logger.getLogger(SpeakerListEventsCommand.class);

    private final UsersEventsServiceImpl usersEventsService = new UsersEventsServiceImpl();


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Commands SpeakerListEventsCommand starts");

        User speaker = (User) request.getSession().getAttribute("user");

        List<Event> events = null;

        try {
            events = usersEventsService.getEventsEventsByUser(speaker);
        } catch (ServiceException daoException) {
            daoException.printStackTrace();
        }

        log.trace("Set the request attribute founded events for speaker: --> " + events);

        request.setAttribute("events", events);

        log.debug("Commands finished");

        return Path.PAGE__PROFILE_USER;
    }

}