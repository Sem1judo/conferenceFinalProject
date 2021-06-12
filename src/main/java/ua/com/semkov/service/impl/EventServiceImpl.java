package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.exceptions.*;
import ua.com.semkov.service.EventService;
import ua.com.semkov.db.entity.Event;


import java.util.List;

public class EventServiceImpl implements EventService {

    private static final Logger log = Logger.getLogger(EventServiceImpl.class);

    private AbstractDao<Event> eventDao;
    DAOProvider daoProvider = DAOProvider.getInstance();


    private int noOfRecords;

    public int getNoOfRecords() {
        return noOfRecords;
    }


    @Override

    public List<Event> getEvents() throws ServiceException {
        List<Event> events;

        eventDao = daoProvider.getEventDao();

        try {
            events = eventDao.getAll();
        } catch (DAOException e) {
            log.error("problem with getting events", e);
            throw new NoSuchEntityException("problem with getting events", e);
        }
        return events;
    }

    @Override
    public List<Event> getEventsPagination(int start, int noOfRecords) throws ServiceException {
        List<Event> events;

        eventDao = daoProvider.getEventDao();

        try {
            events = eventDao.getAllPagination(start, noOfRecords);
            this.noOfRecords = eventDao.getNoOfRecords();
        } catch (DAOException e) {
            log.error("problem with getting events", e);
            throw new NoSuchEntityException(e);
        }
        return events;
    }


    public Event getEvent(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);
        Event event;

        eventDao = daoProvider.getEventDao();

        try {
            event = eventDao.getById(id);
        } catch (DAOException e) {
            log.error("problem with getting event", e);
            throw new NoSuchEntityException("problem with getting event", e);
        }
        return event;
    }

    @Override
    public Event createEvent(Event event) throws ServiceException {
        log.trace("entered event ---> " + event);

        eventDao = daoProvider.getEventDao();

        try {
            eventDao.insertEntity(event);
        } catch (DAOException e) {
            log.error("problem with creating event", e);
            throw new ServiceException("problem with creating event", e);
        }
        return event;
    }

    @Override
    public boolean removeEvent(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);

        eventDao = daoProvider.getEventDao();

        try {
            return eventDao.deleteEntity(id);
        } catch (DAOException e) {
            log.error("problem with removing event", e);
            throw new ServiceException("problem with removing event", e);
        }

    }

    public boolean updateEvent(Event event) throws ServiceException {
        log.trace("entered event ---> " + event);

        eventDao = daoProvider.getEventDao();

        try {
            return eventDao.updateEntityById(event);
        } catch (DAOException e) {
            log.error("problem with updating event", e);
            throw new ServiceException("problem with updating event", e);
        }

    }
}
