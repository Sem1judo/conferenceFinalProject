package ua.com.semkov.db.dao;

import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.dao.impl.TopicDaoImpl;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.db.dao.impl.UsersEventsDaoImpl;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final TopicDaoImpl topicDao = new TopicDaoImpl();
    private final EventDaoImpl eventDao = new EventDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final UsersEventsDaoImpl usersEventsDao = new UsersEventsDaoImpl();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public TopicDaoImpl getTopicDao() {
        return topicDao;
    }

    public EventDaoImpl getEventDao() {
        return eventDao;
    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }

    public UsersEventsDaoImpl getUsersEventsDao() {
        return usersEventsDao;
    }

}