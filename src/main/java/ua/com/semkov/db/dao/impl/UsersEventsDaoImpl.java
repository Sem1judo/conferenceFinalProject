package ua.com.semkov.db.dao.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.DBManager;

import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.db.entity.UsersEvents;
import ua.com.semkov.exceptions.DAOException;


import java.sql.*;
import java.util.ArrayList;

import java.util.List;


public class UsersEventsDaoImpl {

    private static final Logger log = Logger.getLogger(UsersEventsDaoImpl.class.getName());


    private EventDaoImpl eventDao;
    private UserDaoImpl userDao;

    private static final String SQL__GET_ALL_EVENTS_BY_USER_ID =
            "SELECT * FROM users_events WHERE user_id = ?";

    public static final String ADD_USER_EVENT = " INSERT INTO users_events (user_id, event_id) " +
            "SELECT u.id,  e.id " +
            "FROM users u , events e " +
            "WHERE u.id = ? AND e.id = ?";

    public static final String SQL__GET_USERS_BY_EVENT_ID =
            "SELECT * FROM users_events WHERE event_id = ?";

    public static final String SQL__GET_USERS_EVENTS_CONNECTION_EXISTENCE =
            "SELECT * FROM users_events WHERE event_id =? and user_id = ?";

    private static final String SQL__DELETE_JOINED_EVENT =
            "DELETE FROM users_events where  event_id = ? and user_id=?";


    public List<Event> getAllEventsByUser(User user) throws DAOException {
        ResultSet rs = null;
        List<Event> events = new ArrayList<>(0);
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL__GET_ALL_EVENTS_BY_USER_ID)) {

            stmt.setLong(1, user.getId());

            rs = stmt.executeQuery();
            while (rs.next()) {
                eventDao = new EventDaoImpl();
                events.add(eventDao.getById((rs.getLong("event_id"))));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain a list from the database", e);
            throw new DAOException("Getting list from database failed", e);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return events;
    }

    public List<User> getAllUsersByEventId(Long id) throws DAOException {
        ResultSet rs = null;
        List<User> users = new ArrayList<>(0);
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL__GET_USERS_BY_EVENT_ID)) {

            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                userDao = new UserDaoImpl();
                users.add(userDao.getById((rs.getLong("user_id"))));
            }
        } catch (SQLException e) {
            log.error("Cannot obtain a list from the database", e);
            throw new DAOException("Getting list from database failed", e);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return users;
    }

    public void setEventsForUser(User user, Event... events) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        for (Event event : events) {
            try {
                if ((event != null) && (user != null)) {

                    con = DBManager.getInstance().getConnection();
                    con.setAutoCommit(false);
                    con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                    ps = con.prepareStatement(ADD_USER_EVENT);
                    ps.setLong(1, user.getId());
                    ps.setLong(2, event.getId());

                    UsersEvents usersTeams = new UsersEvents();
                    usersTeams.setEventId(event.getId());
                    usersTeams.setUserId(user.getId());

                    int rowsAdded = ps.executeUpdate();
                    if (rowsAdded > 0) {
                        con.commit();
                    }
                } else throw new SQLException("User or Event is null");
            } catch (SQLException e) {
                DBManager.getInstance().rollback(con);
                log.error("Problem with setting Events For User ", e);
            } finally {
                DBManager.getInstance().close(con, ps);
            }
        }
    }


    public boolean isUserJoinedToEvent(Long eventId, Long userId) throws DAOException {
        ResultSet rs = null;
        boolean isJoined = false;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL__GET_USERS_EVENTS_CONNECTION_EXISTENCE)) {

            stmt.setLong(1, eventId);
            stmt.setLong(2, userId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                isJoined = true;
            }
        } catch (SQLException e) {
            log.error("Cannot obtain a list from the database", e);
            throw new DAOException("Getting list from database failed", e);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return isJoined;
    }


    public boolean deleteJoinedEvent(Long eventId,Long userId) throws DAOException {
        boolean isDeleted;
        log.debug("Start method deleteJoinedEvent");

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(SQL__DELETE_JOINED_EVENT)) {

            log.trace("Event ID ---> " + eventId);
            log.trace("User ID ---> " + userId);
            ps.setLong(1, eventId);
            ps.setLong(2, userId);
            ps.executeUpdate();
            isDeleted = true;
        } catch (SQLException ex) {
            log.error("Deleting event failed", ex);
            throw new DAOException("Deleting event failed", ex);
        }
        return isDeleted;
    }
}
