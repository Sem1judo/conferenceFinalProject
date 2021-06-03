package ua.com.semkov.service;

import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public interface EventService {


    List<Event> getEvents() throws ServiceException;

    List<Event> getEventsPagination(int start, int total) throws ServiceException;

    boolean createEvent(Event event) throws ServiceException;

    boolean removeEvent(Long id) throws ServiceException;

    boolean updateEvent(Event event) throws ServiceException;

    Event getEvent(Long id) throws ServiceException;
}
