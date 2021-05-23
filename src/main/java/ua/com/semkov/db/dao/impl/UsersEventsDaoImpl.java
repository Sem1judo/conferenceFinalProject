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

    private static final String SQL__GET_ALL_EVENTS_BY_SPEAKER_ID =
            "SELECT * FROM users_events WHERE user_id = ?";

    public static final String ADD_USER_EVENT = " INSERT INTO users_events (user_id, event_id) " +
            "SELECT u.id,  e.id " +
            "FROM users u , events e " +
            "WHERE u.id = ? AND e.id = ?";


    public List<Event> getAllEventsByUser(User user) throws DAOException {
        ResultSet rs = null;
        List<Event> events = new ArrayList<>(0);
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL__GET_ALL_EVENTS_BY_SPEAKER_ID)) {

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
            close(rs);
        }
        return events;
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
                rollback(con);
                log.error("Problem with setting Events For User ", e);
            } finally {
                close(ps);
                close(con);
            }
        }
    }

    public void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.error("Problem with closing ", e);
            }
        }
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                log.error("Problem with rolling back ", e);
            }
        }
    }
}
