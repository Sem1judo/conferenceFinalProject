package ua.com.semkov.db.dao.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.Topic;

import java.sql.*;
import java.util.List;


public class EventDaoImpl extends AbstractDao<Event> {
    private static final Logger log = Logger.getLogger(EventDaoImpl.class.getName());

    private static final String SQL__FIND_ALL_EVENTS =
            "SELECT * FROM events ORDER BY start_time";

    private static final String SQL__INSERT_EVENT =
            "INSERT INTO events (" +
                    " id, title, description, location, start_time, end_time, organizer_id,status_id) " +
                    " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?,?)";

    private static final String SQL__GET_EVENT_BY_ID =
            "SELECT * FROM events where id = ?";

    private static final String SQL__GET_EVENT_BY_TITLE =
            "SELECT * FROM events where title = ?";

    private static final String SQL__DELETE_EVENT =
            "DELETE FROM events where id = ?";

    public static final String SQL__UPDATE_EVENT = "UPDATE events " +
            "SET title=?, description=?, location=?, start_time=?, end_time=?, organizer_id=?,status_id=? " +
            "WHERE id =?";

    private static final String SQL__FIND_ALL_EVENTS_PAGINATION = " SELECT * FROM events OFFSET ? LIMIT ? ;";
    private static final String SQL__GET_TOTAL_COUNT = "SELECT COUNT(*) AS total FROM events";


    @Override
    public String getQueryGetAll() {
        return SQL__FIND_ALL_EVENTS;
    }

    @Override
    public String getQueryGetById() {
        return SQL__GET_EVENT_BY_ID;
    }

    @Override
    public String getQueryInsertEntity() {
        return SQL__INSERT_EVENT;
    }

    @Override
    public String getQueryDelete() {
        return SQL__DELETE_EVENT;
    }

    @Override
    public String getQueryUpdateById() {
        return SQL__UPDATE_EVENT;
    }

    @Override
    public String getQueryUpdateSpecificName() {
        return null;
    }

    @Override
    public String getQueryGetByName() {
        return SQL__GET_EVENT_BY_TITLE;
    }

    @Override
    public String getQueryGetAllPagination() {
        return SQL__FIND_ALL_EVENTS_PAGINATION;
    }

    @Override
    public String getQueryGetTotalCount() {
        return SQL__GET_TOTAL_COUNT;
    }


    @Override
    public Event mapRow(ResultSet rs) {
        TopicDaoImpl topicDao = new TopicDaoImpl();
        UsersEventsDaoImpl usersEventsDao = new UsersEventsDaoImpl();
        List<Topic> topics = null;
        List<User> users = null;
        try {
            topics = topicDao.getAllTopicsByEventId(rs.getLong("id"));
            users = usersEventsDao.getAllUsersByEventId(rs.getLong("id"));
        } catch (DAOException | SQLException e) {
            log.error("can't take topics when mapping event", e);
        }
        try {
            return new Event.Builder(
                    rs.getString("title")
                    , rs.getTimestamp("start_time").toLocalDateTime()
                    , rs.getTimestamp("end_time").toLocalDateTime()
                    , rs.getLong("organizer_id"))
                    .id(rs.getLong("id"))
                    .statusId(rs.getLong("status_id"))
                    .description(rs.getString("description"))
                    .location(rs.getString("location"))
                    .topics(topics)
                    .users(users)
                    .build();

        } catch (SQLException e) {
            log.error("Problem with mapping event", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Event setEntityId(Long id, Event event) {
        event.setId(id);
        return event;
    }


    public void setRowPS(Event event, PreparedStatement ps) {
        try {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getLocation());
            ps.setTimestamp(4, Timestamp.valueOf(event.getStartTime()));
            ps.setTimestamp(5, Timestamp.valueOf(event.getEndTime()));
            ps.setLong(6, event.getOrganizerId());
            ps.setLong(7, event.getStatusId());
        } catch (SQLException ex) {
            log.error("Problem with setting PreparedStatement from event", ex);
        }
    }

    @Override
    public void setIdPS(Event event, PreparedStatement ps) {
        try {
            ps.setLong(8, event.getId());
        } catch (SQLException ex) {
            log.error("Problem with setting ID for PreparedStatement from event", ex);
        }
    }


}
