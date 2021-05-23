package ua.com.semkov.service;

import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;

import java.util.List;

public interface UserService {


    void registration(User user) throws ServiceException;


    List<User> getUsers() throws ServiceException;

    User getUser(String login) throws ServiceException;

    User getUserById(Long id) throws ServiceException;


    void updateUserUsername(int userID, String newUserLogin) throws ServiceException;

    void updateUserPassword(int userID, String oldPassword, String newPassword) throws ServiceException;
}