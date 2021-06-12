package ua.com.semkov.service.impl;


import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.NoSuchEntityException;
import ua.com.semkov.service.UserService;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.exceptions.DAOException;

import ua.com.semkov.exceptions.ServiceException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private UserDaoImpl userDao;
    DAOProvider daoProvider = DAOProvider.getInstance();



    public boolean isEmpty(String login, String password) {
        return (login == null || password == null || login.isEmpty() || password.isEmpty());
    }


    @Override
    public User getUser(String login) throws ServiceException {

        userDao = daoProvider.getUserDao();

        User user;

        try {
            user = userDao.getBySpecificName(login);
        } catch (DAOException e) {
            log.error("problem with getting user by login", e);
            throw new NoSuchEntityException(e);
        }
        return user;

    }

    @Override
    public User getUserById(Long id) throws ServiceException {

        userDao = daoProvider.getUserDao();
        User user;

        try {
            user = userDao.getById(id);
        } catch (DAOException e) {
            log.error("problem with getting user by id", e);
            throw new NoSuchEntityException("Problem with getting user", e);
        }
        return user;
    }

    public boolean updateUser(User user) throws ServiceException {
        log.trace("entered user ---> " + user);

        userDao = daoProvider.getUserDao();

        try {
            return userDao.updateEntityById(user);
        } catch (DAOException e) {
            log.error("problem with updating user", e);
            throw new ServiceException("problem with updating user", e);
        }

    }


    @Override
    public User registration(User data) throws ServiceException {
        userDao = daoProvider.getUserDao();
        User user;

        try {
            data.setPassword(Encoder.encrypt(data.getPassword()));

            user = new User.Builder(
                    data.getLogin()
                    , data.getPassword()
                    , data.getEmail()
                    , data.getPhone())
                    .id(data.getId())
                    .roleId(2)
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .build();
            user = userDao.insertEntity(user);

        } catch (DAOException | NoSuchAlgorithmException e) {
            log.error("problem with registration user", e);
            throw new ServiceException(e);
        }
        return user;
    }


    public List<User> getUsers() throws ServiceException {
        List<User> allUsers;

        userDao = daoProvider.getUserDao();

        try {
            allUsers = userDao.getAll();

        } catch (DAOException e) {
            log.error("problem with getting users", e);
            throw new NoSuchEntityException(e);
        }

        return allUsers;
    }


    @Override
    public boolean updateUserPassword(Long userID, String oldPassword, String newPassword) throws ServiceException {
        userDao = daoProvider.getUserDao();

        try {
            oldPassword = Encoder.encrypt(oldPassword);
            newPassword = Encoder.encrypt(newPassword);

            return userDao.updateUserPassword(userID, oldPassword, newPassword);
        } catch (NoSuchAlgorithmException | DAOException e) {
            log.error("problem with updating user", e);
            throw new ServiceException(e);
        }
    }
}
