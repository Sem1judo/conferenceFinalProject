package ua.com.semkov.service.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.dao.impl.TopicDaoImpl;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.db.dao.DAOProvider;
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
    DAOProvider daoProvider = DAOProvider.getInstance();
    private int noOfRecords;


    @Override
    public List<TopicDto> getTopicsDtoByEvent(Long eventId) throws ServiceException {
        List<Topic> topics;
        User speaker = null;
        Event event = null;

        List<TopicDto> topicDtos = new ArrayList<>();


        topicDao = daoProvider.getTopicDao();
        userDao = daoProvider.getUserDao();
        eventDao = daoProvider.getEventDao();

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
            topicDto.setConfirm(topic.getConfirm());

            topicDtos.add(topicDto);
        }
        return topicDtos;
    }

    @Override
    public List<Topic> getTopicsPagination(int start, int noOfRecords) throws ServiceException {
        List<Topic> topics;


        topicDao = daoProvider.getTopicDao();

        try {
            topics = topicDao.getAllPagination(start, noOfRecords);
            this.noOfRecords = topicDao.getNoOfRecords();
        } catch (DAOException e) {
            log.error("problem with getting topics", e);
            throw new ServiceException("problem with getting topics", e);
        }
        return topics;
    }


    @Override
    public List<Topic> getTopics() throws ServiceException {
        List<Topic> topics;

        topicDao = daoProvider.getTopicDao();

        try {
            topics = topicDao.getAll();
        } catch (DAOException e) {
            log.error("problem with getting topics", e);
            throw new ServiceException("problem with getting topics for event", e);
        }

        return topics;
    }


    public List<TopicDto> getTopicsDto() throws ServiceException {
        List<Topic> topics = getTopics();
        List<TopicDto> topicDtos = new ArrayList<>();

        userDao = daoProvider.getUserDao();
        eventDao = daoProvider.getEventDao();

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
            topicDto.setConfirm(topic.getConfirm());


            topicDtos.add(topicDto);

        }
        return topicDtos;

    }

    @Override
    public List<TopicDto> getTopicsDtoPaginationConfirmed(int start, int noOfRecords) throws ServiceException {
        List<Topic> topics;
        List<TopicDto> topicDtos = new ArrayList<>();

        topicDao = daoProvider.getTopicDao();
        try {
            topics = topicDao.getAllPagination(start, noOfRecords);
        } catch (DAOException e) {
            log.error("problem with getting topics", e);
            throw new ServiceException("problem with getting topics", e);
        }

        userDao = daoProvider.getUserDao();
        eventDao = daoProvider.getEventDao();

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
            topicDto.setConfirm(topic.getConfirm());

            topicDtos.add(topicDto);

        }
        this.noOfRecords = topicDao.getNoOfRecords();
        return topicDtos;

    }


    @Override
    public TopicDto getTopicDtoById(Long id) throws ServiceException {
        Topic topic;
        User speaker;
        Event event;


        topicDao = daoProvider.getTopicDao();
        userDao = daoProvider.getUserDao();
        eventDao = daoProvider.getEventDao();

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
        topicDto.setConfirm(topic.getConfirm());


        return topicDto;
    }

    public Topic getTopicById(Long id) throws ServiceException {
        log.trace("entered topic id---> " + id);
        Topic topic;

        topicDao = daoProvider.getTopicDao();


        try {
            topic = topicDao.getById(id);
        } catch (DAOException e) {
            log.error("problem with getting topic", e);
            throw new ServiceException("problem with getting topic", e);
        }
        return topic;
    }


    @Override
    public Topic createTopic(Topic topic) throws ServiceException {

        log.trace("entered topic ---> " + topic);

        topicDao = daoProvider.getTopicDao();

        try {
            topicDao.insertEntity(topic);

        } catch (DAOException e) {
            log.error("problem with creating topic", e);
            throw new ServiceException("problem with creating topic", e);
        }
        return topic;
    }

    @Override
    public boolean removeTopic(Long id) throws ServiceException {
        log.trace("entered event id---> " + id);

        topicDao = daoProvider.getTopicDao();

        try {
            return topicDao.deleteEntity(id);
        } catch (DAOException e) {
            log.error("problem with removing topic", e);
            throw new ServiceException("problem with removing topic", e);
        }

    }

    @Override
    public boolean updateTopic(Topic topic) throws ServiceException {
        log.trace("entered topic ---> " + topic);

        topicDao = daoProvider.getTopicDao();

        try {
            return topicDao.updateEntityById(topic);
        } catch (DAOException e) {
            log.error("problem with updating topic", e);
            throw new ServiceException("problem with updating topic", e);
        }
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
}
