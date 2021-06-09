package ua.com.semkov.service.impl;


import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.util.ReflectionTestUtils;
import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.dao.impl.TopicDaoImpl;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;


class TopicServiceImplTest {

    @Mock
    private TopicDaoImpl topicDao;
    @Mock
    private UserDaoImpl userDao;
    @Mock
    private EventDaoImpl eventDao;

    @InjectMocks
    TopicServiceImpl topicService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        topicService = new TopicServiceImpl();
        ReflectionTestUtils.setField(topicService, "topicDao", topicDao);
        ReflectionTestUtils.setField(topicService, "userDao", userDao);
        ReflectionTestUtils.setField(topicService, "eventDao", eventDao);


    }

    @Test
    public void shouldGetTopicByIdWhenTopicExist() throws DAOException, ServiceException {
        Mockito.when(topicDao.getById(1L)).thenReturn(createTestEntity());
        Topic actual = topicService.getTopicById(1L);
        Assertions.assertEquals("Name", actual.getName());
        Assertions.assertEquals("Desc", actual.getDescription());
        Mockito.verify(topicDao).getById(1L);
    }

    private Topic createTestEntity() {
        Topic topic = new Topic("Name", "Desc");
        return topic;
    }

}