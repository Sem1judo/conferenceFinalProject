package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.impl.UsersEventsDaoImpl;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;


public class UsersEventsServiceImpl {
    private static final Logger log = Logger.getLogger(UsersEventsServiceImpl.class);

    private UsersEventsDaoImpl usersEventsDao;
    DAOProvider daoProvider = DAOProvider.getInstance();



    public List<Event> getEventsEventsByUser(User user) throws ServiceException {
        List<Event> events;

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

        usersEventsDao = daoProvider.getUsersEventsDao();

        try {
            return usersEventsDao.deleteJoinedEvent(eventId, userId);
        } catch (DAOException e) {
            log.error("problem with removing event from user", e);
            throw new ServiceException("problem with removing event from user", e);
        }

    }
}
