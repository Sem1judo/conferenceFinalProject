package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.exceptions.*;
import ua.com.semkov.service.EventService;
import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.entity.Event;


import java.util.List;

public class EventServiceImpl implements EventService {

    private static final Logger log = Logger.getLogger(EventServiceImpl.class);

    private EventDaoImpl eventDao;

    private int noOfRecords;

    @Override

    public List<Event> getEvents() throws ServiceException {
        List<Event> events;

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            events = eventDao.getAll();
        } catch (EntityAlreadyExistsDAOException e) {
            log.error("problem with getting events", e);
            throw new EntityAlreadyExistsServiceException("problem with getting events", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return events;
    }

    @Override
    public List<Event> getEventsPagination(int start, int noOfRecords) throws ServiceException {
        List<Event> events;

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            events = eventDao.getAllPagination(start, noOfRecords);
            this.noOfRecords = eventDao.getNoOfRecords();
        } catch (DAOException e) {
            log.error("problem with getting events", e);
            throw new ServiceException(e);
        }
        return events;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }


    public Event getEvent(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);
        Event event;

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            event = eventDao.getById(id);
        } catch (DAOException e) {
            log.error("problem with getting event", e);
            throw new ServiceException("problem with getting event", e);
        }
        return event;
    }

    @Override
    public void createEvent(Event event) throws ServiceException {
        log.trace("entered event ---> " + event);

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            eventDao.insertEntityReturningId(event);
        } catch (EntityAlreadyExistsDAOException e) {
            log.error("already exist event", e);
            throw new EntityAlreadyExistsServiceException("already exist event", e);
        } catch (DAOException e) {
            log.error("problem with creating event", e);
            throw new ServiceException("problem with creating event", e);
        }

    }

    @Override
    public void removeEvent(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            eventDao.deleteEntity(id);
        } catch (DAOException e) {
            log.error("problem with removing event", e);
            throw new ServiceException("problem with removing event", e);
        }

    }

    public void updateEvent(Event event) throws ServiceException {
        log.trace("entered event ---> " + event);

        DAOProvider daoProvider = DAOProvider.getInstance();
        eventDao = daoProvider.getEventDao();

        try {
            eventDao.updateEntityById(event);
        } catch (DAOException e) {
            log.error("problem with updating event", e);
            throw new ServiceException("problem with updating event", e);
        }

    }
}
