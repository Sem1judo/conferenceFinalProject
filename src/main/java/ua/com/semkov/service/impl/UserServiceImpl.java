package ua.com.semkov.service.impl;


import org.apache.log4j.Logger;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.service.UserService;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.EntityAlreadyExistsDAOException;
import ua.com.semkov.exceptions.EntityAlreadyExistsServiceException;
import ua.com.semkov.exceptions.ServiceException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private static final int MIN_EMAIL_LENGTH = 5;
    private UserDaoImpl userDao;


    public boolean isEmpty(String login, String password) {
        return (login == null || password == null || login.isEmpty() || password.isEmpty());
    }


    @Override
    public User getUser(String login) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();

        User user;

        try {
            user = userDao.getBySpecificName(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;

    }

    @Override
    public User getUserById(Long id) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();
        User user;

        try {
            user = userDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Problem with getting user", e);
        }
        return user;
    }


    @Override
    public void registration(User data) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();

        try {
            data.setPassword(Encoder.encrypt(data.getPassword()));

            String email = data.getEmail();
            if (email != null && email.length() < MIN_EMAIL_LENGTH) {
                data.setEmail(null);
            }
            User user = new User.Builder(
                    data.getLogin()
                    , data.getPassword()
                    , data.getEmail()
                    , data.getPhone())
                    .roleId(2)
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .build();
            userDao.insertEntityReturningId(user);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }


    public List<User> getUsers() throws ServiceException {
        List<User> allUsers;

        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();

        try {
            allUsers = userDao.getAll();
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allUsers;
    }


    @Override
    public boolean updateUserUsername(Long userID, String newUserLogin) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();

        try {
            return userDao.updateUserLogin(userID, newUserLogin);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean updateUserPassword(Long userID, String oldPassword, String newPassword) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        userDao = daoProvider.getUserDao();

        try {
            oldPassword = Encoder.encrypt(oldPassword);
            newPassword = Encoder.encrypt(newPassword);

            return userDao.updateUserPassword(userID, oldPassword, newPassword);
        } catch (NoSuchAlgorithmException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
