package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.dao.impl.TopicDaoImpl;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.*;
import ua.com.semkov.service.TopicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TopicServiceImpl implements TopicService {

    private static final Logger log = Logger.getLogger(TopicServiceImpl.class);

    private TopicDaoImpl topicDao;
    private UserDaoImpl userDao;
    private EventDaoImpl eventDao;
    private int noOfRecords;

    @Override
    public List<TopicDto> getTopicsDtoByEvent(Long eventId) throws ServiceException {
        List<Topic> topics;
        User speaker = null;
        Event event = null;

        List<TopicDto> topicDtos = new ArrayList<>();

        topicDao = new TopicDaoImpl();
        userDao = new UserDaoImpl();
        eventDao = new EventDaoImpl();

        try {
            topics = topicDao.getAllTopicsByEventId(eventId);
        } catch (DAOException e) {
            log.error("problem with getting topics by event id", e);
            throw new ServiceException("problem with getting topics by event id", e);
        }

        for (Topic topic : topics) {
            try {
                speaker = Optional.of(userDao.getById(topic.getUserId()))
                        .orElseThrow(() -> new NoSuchEntityException("Invalid user ID"));

                event = Optional.of(eventDao.getById(topic.getEventId()))
                        .orElseThrow(() -> new NoSuchEntityException("Invalid event ID"));

            } catch (DAOException e) {
                log.error("problem with getting userId or eventId", e);
            }

            TopicDto topicDto = new TopicDto();
            topicDto.setId(topic.getId());
            topicDto.setName(topic.getName());
            topicDto.setDescription(topic.getDescription());
            topicDto.setEvent(event);
            topicDto.setSpeaker(speaker);

            topicDtos.add(topicDto);
        }
        return topicDtos;
    }

    @Override
    public List<Topic> getTopicsPagination(int start, int noOfRecords) throws ServiceException {
        List<Topic> topics;

        topicDao = new TopicDaoImpl();
        try {
            topics = topicDao.getAllPagination(start, noOfRecords);
            this.noOfRecords = topicDao.getNoOfRecords();
        } catch (DAOException e) {
            log.error("problem with getting topics", e);
            throw new ServiceException("problem with getting topics", e);
        }
        return topics;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Topic> getTopics() throws ServiceException {
        List<Topic> topics;
        topicDao = new TopicDaoImpl();
        try {
            topics = topicDao.getAll();
        } catch (DAOException e) {
            log.error("problem with getting topics", e);
            throw new ServiceException("problem with getting topics for event", e);
        }

        return topics;
    }


    @Override
    public List<TopicDto> getTopicsDto() throws ServiceException {
        List<Topic> topics = getTopics();
        List<TopicDto> topicDtos = new ArrayList<>();
        userDao = new UserDaoImpl();
        eventDao = new EventDaoImpl();

        User speaker;
        Event event;

        for (Topic topic : topics) {
            try {
                speaker = Optional.of(userDao.getById(topic.getUserId()))
                        .orElseThrow(() -> new NoSuchEntityException("Invalid user ID"));

                event = Optional.of(eventDao.getById(topic.getEventId()))
                        .orElseThrow(() -> new NoSuchEntityException("Invalid event ID"));

            } catch (DAOException e) {
                log.error("problem with getting userId or eventId", e);
                throw new ServiceException("problem with getting topics DTO", e);
            }

            TopicDto topicDto = new TopicDto();
            topicDto.setName(topic.getName());
            topicDto.setId(topic.getId());
            topicDto.setDescription(topic.getDescription());
            topicDto.setEvent(event);
            topicDto.setSpeaker(speaker);

            topicDtos.add(topicDto);
        }
        return topicDtos;

    }

    @Override
    public List<TopicDto> getTopicsDtoPagination(int start, int noOfRecords) throws ServiceException {
        List<Topic> topics = getTopicsPagination(start, noOfRecords);
        List<TopicDto> topicDtos = new ArrayList<>();

        userDao = new UserDaoImpl();
        eventDao = new EventDaoImpl();

        User speaker;
        Event event;

        for (Topic topic : topics) {
            try {
                speaker = Optional.of(userDao.getById(topic.getUserId()))
                        .orElseThrow(() -> new ServiceException("Invalid user ID"));

                event = Optional.of(eventDao.getById(topic.getEventId()))
                        .orElseThrow(() -> new ServiceException("Invalid event ID"));

            } catch (DAOException e) {
                log.error("problem with getting userId or eventId", e);
                throw new ServiceException("problem with getting topics DTO", e);
            }

            TopicDto topicDto = new TopicDto();
            topicDto.setName(topic.getName());
            topicDto.setId(topic.getId());
            topicDto.setDescription(topic.getDescription());
            topicDto.setEvent(event);
            topicDto.setSpeaker(speaker);

            topicDtos.add(topicDto);
        }
        return topicDtos;

    }


    @Override
    public TopicDto getTopicDtoById(Long id) throws ServiceException {
        Topic topic = null;
        User speaker = null;
        Event event = null;

        topicDao = new TopicDaoImpl();
        userDao = new UserDaoImpl();
        eventDao = new EventDaoImpl();

        try {
            topic = topicDao.getById(id);
            speaker = Optional.of(userDao.getById(topic.getUserId()))
                    .orElseThrow(() -> new NoSuchEntityException("Invalid user ID"));

            event = Optional.of(eventDao.getById(topic.getEventId()))
                    .orElseThrow(() -> new NoSuchEntityException("Invalid event ID"));
        } catch (DAOException e) {
            log.error("problem with getting userId or eventId", e);
            throw new ServiceException("problem with getting topic DTO", e);
        }

        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        topicDto.setDescription(topic.getDescription());
        topicDto.setEvent(event);
        topicDto.setSpeaker(speaker);

        return topicDto;
    }

    public Topic getTopicById(Long id) throws ServiceException {
        log.trace("entered topic id---> " + id);
        Topic topic;

        topicDao = new TopicDaoImpl();


        try {
            topic = topicDao.getById(id);
        } catch (DAOException e) {
            log.error("problem with getting topic", e);
            throw new ServiceException("problem with getting topic", e);
        }
        return topic;
    }


    @Override
    public void createTopic(Topic topic) throws ServiceException {
        log.trace("entered topic ---> " + topic);

        topicDao = new TopicDaoImpl();

        try {
            topicDao.insertEntityReturningId(topic);
        } catch (EntityAlreadyExistsDAOException e) {
            log.error("already exist topic", e);
            throw new EntityAlreadyExistsServiceException("already exist topic", e);
        } catch (DAOException e) {
            log.error("problem with creating topic", e);
            throw new ServiceException("problem with creating topic", e);
        }

    }

    @Override
    public void removeTopic(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);

        topicDao = new TopicDaoImpl();

        try {
            topicDao.deleteEntity(id);
        } catch (DAOException e) {
            log.error("problem with removing topic", e);
            throw new ServiceException("problem with removing topic", e);
        }

    }

    @Override
    public void updateTopic(Topic topic) throws ServiceException {
        log.trace("entered topic ---> " + topic);

        topicDao = new TopicDaoImpl();

        try {
            topicDao.updateEntityById(topic);
        } catch (DAOException e) {
            log.error("problem with updating topic", e);
            throw new ServiceException("problem with updating topic", e);
        }
    }


}
