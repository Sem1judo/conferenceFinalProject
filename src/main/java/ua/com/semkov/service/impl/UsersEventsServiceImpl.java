package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.impl.UsersEventsDaoImpl;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public class UsersEventsServiceImpl {
    private static final Logger log = Logger.getLogger(UsersEventsServiceImpl.class);

    private UsersEventsDaoImpl usersEventsDao;

    private int noOfRecords;

    public List<Event> getEventsEventsByUser(User user) throws ServiceException {
        List<Event> events;

        usersEventsDao = new UsersEventsDaoImpl();

        try {
            events = usersEventsDao.getAllEventsByUser(user);

        } catch (DAOException e) {
            log.error("problem with getting events for speaker", e);
            throw new ServiceException("problem with getting events for speaker", e);
        }

        return events;
    }

    public void setEventsForUser(User user, Event... events) throws ServiceException {
        usersEventsDao = new UsersEventsDaoImpl();
        try {
            usersEventsDao.setEventsForUser(user, events);
        } catch (DAOException e) {
            log.error("problem with getting events for speaker", e);
            throw new ServiceException("problem with getting events for speaker", e);
        }
    }

    public List<User> getAllUsersByEventId(Long id) throws ServiceException {

        List<User> users;

        usersEventsDao = new UsersEventsDaoImpl();

        try {
            users = usersEventsDao.getAllUsersByEventId(id);

        } catch (
                DAOException e) {
            log.error("problem with getting users for event", e);
            throw new ServiceException("problem with getting users for event", e);
        }

        return users;
    }
}
