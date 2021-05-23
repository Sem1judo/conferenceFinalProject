package ua.com.semkov.service.impl;


import org.apache.log4j.Logger;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.service.UserService;
import ua.com.semkov.db.dao.AbstractDao;
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
    private UserDaoImpl userDAO;


    public boolean isEmpty(String login, String password) {
        return (login == null || password == null || login.isEmpty() || password.isEmpty());
    }


    @Override
    public User getUser(String login) throws ServiceException {
        userDAO = new UserDaoImpl();
        User user;

        try {
            user = userDAO.getBySpecificName(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;

    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        userDAO = new UserDaoImpl();
        User user;

        try {
            user = userDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Problem with getting user", e);
        }
        return user;
    }


    @Override
    public void registration(User data) throws ServiceException {
        AbstractDao<User> userDAO = new UserDaoImpl();

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
            userDAO.insertEntityReturningId(user);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }


    public List<User> getUsers() throws ServiceException {
        List<User> allUsers;

        AbstractDao<User> userDAO = new UserDaoImpl();

        try {
            allUsers = userDAO.getAll();
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allUsers;
    }


    @Override
    public void updateUserUsername(int userID, String newUserLogin) throws ServiceException {

    }

    @Override
    public void updateUserPassword(int userID, String oldPassword, String newPassword) throws ServiceException {

    }

//    @Override
//    public void updateUserUsername(int userID, String newUserLogin) throws ServiceException {
//        AbstractDao<User> userDAO = new UserDaoImpl();
//
//        try {
//            userDAO.updateUserLogin(userID, newUserLogin);
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//    }
//
//    @Override
//    public void updateUserPassword(int userID, String oldPassword, String newPassword) throws ServiceException {
//        AbstractDao<User> userDAO = new UserDaoImpl();
//
//        try {
//            oldPassword = Encoder.encrypt(oldPassword);
//            newPassword = Encoder.encrypt(newPassword);
//
//            userDAO.updateUserPassword(userID, oldPassword, newPassword);
//        } catch (DAOException | NoSuchAlgorithmException e) {
//            throw new ServiceException(e);
//        }
//    }
}
