package ua.com.semkov.service;

import ua.com.semkov.db.dto.TopicDto;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicsPagination(int start, int noOfRecords) throws ServiceException;

    List<Topic> getTopics() throws ServiceException;

    List<TopicDto> getTopicsDtoByEvent(Long eventId) throws ServiceException;

    List<TopicDto> getTopicsDtoPaginationConfirmed(int start, int noOfRecords) throws ServiceException;

    TopicDto getTopicDtoById(Long id) throws ServiceException;

    Topic createTopic(Topic topic) throws ServiceException;

    boolean removeTopic(Long id) throws ServiceException;

    boolean updateTopic(Topic topic) throws ServiceException;
}
