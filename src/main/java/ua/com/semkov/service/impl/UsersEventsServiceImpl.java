package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.impl.UsersEventsDaoImpl;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public class UsersEventsServiceImpl {
    private static final Logger log = Logger.getLogger(UsersEventsServiceImpl.class);

    private UsersEventsDaoImpl usersEventsDao;


    public List<Event> getEventsEventsByUser(User user) throws ServiceException {
        List<Event> events;

        DAOProvider daoProvider = DAOProvider.getInstance();
        usersEventsDao = daoProvider.getUsersEventsDao();

        try {
            events = usersEventsDao.getAllEventsByUser(user);

        } catch (DAOException e) {
            log.error("problem with getting events for speaker", e);
            throw new ServiceException("problem with getting events for speaker", e);
        }

        return events;
    }

    public void setEventsForUser(User user, Event... events) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        usersEventsDao = daoProvider.getUsersEventsDao();

        try {
            usersEventsDao.setEventsForUser(user, events);
        } catch (DAOException e) {
            log.error("problem with getting events for speaker", e);
            throw new ServiceException("problem with getting events for speaker", e);
        }
    }


    public List<User> getAllUsersByEventId(Long id) throws ServiceException {

        List<User> users;

        DAOProvider daoProvider = DAOProvider.getInstance();
        usersEventsDao = daoProvider.getUsersEventsDao();

        try {
            users = usersEventsDao.getAllUsersByEventId(id);

        } catch (
                DAOException e) {
            log.error("problem with getting users for event", e);
            throw new ServiceException("problem with getting users for event", e);
        }

        return users;
    }

    public boolean isUserJoinedToEvent(Event event, User user) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        usersEventsDao = daoProvider.getUsersEventsDao();

        boolean isJoined;

        try {
            isJoined = usersEventsDao.isUserJoinedToEvent(event.getId(), user.getId());
        } catch (
                DAOException e) {
            log.error("problem with getting users for event", e);
            throw new ServiceException("problem with getting users for event", e);
        }
        return isJoined;
    }


    public boolean removeJoinedEvent(Long eventId, Long userId) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        usersEventsDao = daoProvider.getUsersEventsDao();

        try {
            return usersEventsDao.deleteJoinedEvent(eventId, userId);
        } catch (DAOException e) {
            log.error("problem with removing event from user", e);
            throw new ServiceException("problem with removing event from user", e);
        }

    }
}
