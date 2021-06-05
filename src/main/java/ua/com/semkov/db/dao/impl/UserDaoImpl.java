package ua.com.semkov.db.dao.impl;

import org.apache.log4j.Logger;

import ua.com.semkov.db.entity.Topic;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Data access object for User entity.
 */
public class UserDaoImpl extends AbstractDao<User> {

    private static final Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    private static final String SQL__FIND_ALL_USERS =
            "SELECT * FROM users";

    private static final String SQL__INSERT_USER =
            "INSERT INTO users" +
                    " (login, password, first_name, last_name, email, phone, role_id,locale_name) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?,?)";


    private static final String SQL__GET_USER_BY_ID =
            "SELECT * FROM users WHERE id = ?";

    private static final String SQL__GET_USER_BY_LOGIN =
            "SELECT * FROM users WHERE login = ?";

    private static final String SQL__DELETE_USER =
            "DELETE FROM users WHERE id = ?";

    public static final String SQL__UPDATE_USER =
            "UPDATE users " +
                    "SET first_name=?, last_name=?, email=?, password=?, login=?, phone=?, role_id=? ,locale_name=?" +
                    " WHERE id = ?";

    private static final String SELECT_USER_BY_LOGIN_AND_PASS =
            "SELECT u.id, u.login, u.create_time " +
                    "FROM users u JOIN role rol ON u.role_id=rol.id " +
                    "WHERE u.login=? " +
                    "and u.password=?";


    @Override
    public String getQueryGetAll() {
        return SQL__FIND_ALL_USERS;
    }

    @Override
    public String getQueryGetById() {
        return SQL__GET_USER_BY_ID;
    }

    @Override
    public String getQueryInsertEntity() {
        return SQL__INSERT_USER;
    }

    @Override
    public String getQueryDelete() {
        return SQL__DELETE_USER;
    }

    @Override
    public String getQueryUpdateById() {
        return SQL__UPDATE_USER;
    }

    @Override
    public String getQueryUpdateSpecificName() {
        return null;
    }

    @Override
    public String getQueryGetByName() {
        return SQL__GET_USER_BY_LOGIN;
    }

    @Override
    public String getQueryGetAllPagination() {
        return null;
    }

    @Override
    public String getQueryGetTotalCount() {
        return null;
    }

    public void setRowPS(User user, PreparedStatement ps) {
        int k = 1;
        try {
            ps.setString(k++, user.getFirstName());
            ps.setString(k++, user.getLastName());
            ps.setString(k++, user.getEmail());
            ps.setString(k++, user.getPassword());
            ps.setString(k++, user.getLogin());
            ps.setString(k++, user.getPhone());
            ps.setInt(k++, user.getRoleId());
            ps.setString(k++, user.getLocaleName());
        } catch (SQLException ex) {
            log.error("Problem with setting PreparedStatement from user", ex);
        }
    }

    @Override
    public void setIdPS(User user, PreparedStatement ps) {
        try {
            ps.setLong(9, user.getId());
        } catch (SQLException ex) {
            log.error("Problem with setting PreparedStatement from user for id", ex);
        }
    }


    @Override
    public User mapRow(ResultSet rs) {
        TopicDaoImpl topicDao = new TopicDaoImpl();
        List<Topic> topics = null;
        try {
            topics = topicDao.getAllTopicsByEventId(rs.getLong("id"));
        } catch (DAOException | SQLException e) {
            log.error("can't take topics when mapping user", e);
        }
        try {
            return new User.Builder(
                    rs.getString("login")
                    , rs.getString("password")
                    , rs.getString("email")
                    , rs.getString("phone"))
                    .roleId(rs.getInt("role_id"))
                    .id(rs.getLong("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .registrationDate(rs.getTimestamp("registration_date").toLocalDateTime())
                    .localeName(rs.getString("locale_name"))
                    .topics(topics)
                    .build();
        } catch (SQLException e) {
            log.error("Problem with mapping user", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User setEntityId(Long id, User user) {
        user.setId(id);
        return user;
    }

    public boolean updateUserPassword(Long userID, String oldPassword, String newPassword) throws DAOException {
        return true;
    }

    public boolean updateUserLogin(Long userID, String newUserLogin) throws DAOException {
        return true;
    }


}
