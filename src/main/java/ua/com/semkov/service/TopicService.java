package ua.com.semkov.service;

import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicsPagination(int start, int noOfRecords) throws ServiceException;

    List<Topic> getTopics() throws ServiceException;

    List<TopicDto> getTopicsDtoByEvent(Long eventId) throws ServiceException;

    List<TopicDto> getTopicsDto() throws ServiceException;

    List<TopicDto> getTopicsDtoPagination(int start, int noOfRecords) throws ServiceException;

    TopicDto getTopicDtoById(Long id) throws ServiceException;

    void createTopic(Topic topic) throws ServiceException;

    void removeTopic(Long id) throws ServiceException;

    void updateTopic(Topic topic) throws ServiceException;
}
