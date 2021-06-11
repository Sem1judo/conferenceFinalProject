package ua.com.semkov.db.dao;

import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.DAOException;

import java.util.List;

public interface InterfaceUsersEvent<T> {

    List<Event> getAllEventsByUser(User user) throws DAOException;

    boolean isUserJoinedToEvent(Long eventId, Long userId) throws DAOException;

    boolean deleteJoinedEvent(Long eventId, Long userId) throws DAOException;

    void setEventsForUser(User user, Event... events) throws DAOException;

    List<User> getAllUsersByEventId(Long id) throws DAOException;
}
