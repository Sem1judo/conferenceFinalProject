package ua.com.semkov.service;

import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public interface UserService {


    User registration(User user) throws ServiceException;


    List<User> getUsers() throws ServiceException;

    User getUser(String login) throws ServiceException;

    User getUserById(Long id) throws ServiceException;


    boolean updateUserUsername(Long userID, String newUserLogin) throws ServiceException;

    boolean updateUserPassword(Long userID, String oldPassword, String newPassword) throws ServiceException;
}