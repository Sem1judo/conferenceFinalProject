package ua.com.semkov.db.dao.impl;

import org.apache.log4j.Logger;
import ua.com.semkov.db.DBManager;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.db.entity.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl extends AbstractDao<Topic> {

    private static final Logger log = Logger.getLogger(TopicDaoImpl.class.getName());

    private static final String SQL__FIND_ALL_TOPICS =
            "SELECT * FROM topics";

    private static final String SQL__INSERT_TOPIC =
            "INSERT INTO topics VALUES (default, ?, ?, ?, ?, ?)";

    private static final String SQL__GET_TOPIC_BY_ID =
            "SELECT * FROM topics where id = ?";

    private static final String SQL__GET_TOPIC_BY_NAME =
            "SELECT * FROM topics where name = ?";

    private static final String SQL__DELETE_TOPIC =
            "DELETE FROM topics where id = ?";

    public static final String SQL__UPDATE_TOPIC_BY_ID = "UPDATE topics " +
            "SET name=?, description=?, user_id=?, event_id=?, confirm=? " +
            "WHERE id=?";


    public static final String SQL__GET_ALL_TOPICS_BY_EVENT_ID = "SELECT id, name, description, user_id, event_id, confirm " +
            " FROM topics WHERE event_id = ?";
    public static final String SQL__GET_ALL_TOPICS_BY_USER_ID = "SELECT id, name, description, user_id, event_id, confirm" +
            " FROM topics WHERE user_id = ?";

    private static final String SQL__FIND_ALL_TOPICS_PAGINATION = " SELECT * FROM topics OFFSET ? LIMIT ? ;";
    private static final String SQL__GET_TOTAL_COUNT = "SELECT COUNT(*) AS total FROM topics where confirm = true";


    @Override
    public String getQueryGetAll() {
        return SQL__FIND_ALL_TOPICS;
    }

    @Override
    public String getQueryGetById() {
        return SQL__GET_TOPIC_BY_ID;
    }

    @Override
    public String getQueryInsertEntity() {
        return SQL__INSERT_TOPIC;
    }

    @Override
    public String getQueryDelete() {
        return SQL__DELETE_TOPIC;
    }

    @Override
    public String getQueryUpdateById() {
        return SQL__UPDATE_TOPIC_BY_ID;
    }

    @Override
    public String getQueryUpdateSpecificName() {
        return null;
    }

    @Override
    public String getQueryGetByName() {
        return null;
    }

    @Override
    public String getQueryGetAllPagination() {
        return SQL__FIND_ALL_TOPICS_PAGINATION;
    }

    @Override
    public String getQueryGetTotalCount() {
        return SQL__GET_TOTAL_COUNT;
    }


    public List<Topic> getAllTopicsByEventId(Long eventId) throws DAOException {
        return getTopics(eventId, SQL__GET_ALL_TOPICS_BY_EVENT_ID);
    }

    public List<Topic> getAllTopicsByUserId(Long userId) throws DAOException {
        return getTopics(userId, SQL__GET_ALL_TOPICS_BY_USER_ID);
    }

    private List<Topic> getTopics(Long userId, String sql_getAllTopicsByUserId) throws DAOException {
        List<Topic> topics = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql_getAllTopicsByUserId)) {
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            while (rs.next())
                topics.add(mapRow(rs));
        } catch (SQLException ex) {
            log.error("Getting by id failed", ex);
            throw new DAOException("Getting by id failed", ex);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return topics;
    }

    @Override
    public void setRowPS(Topic topic, PreparedStatement ps) {
        try {
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getDescription());
            ps.setLong(3, topic.getUserId());
            ps.setLong(4, topic.getEventId());
            ps.setBoolean(5, topic.getConfirm());
        } catch (SQLException ex) {
            log.error("Problem with setting PreparedStatement from topic", ex);
        }

    }

    @Override
    public void setIdPS(Topic topic, PreparedStatement ps) {
        try {
            ps.setLong(6, topic.getId());
        } catch (SQLException ex) {
            log.error("Problem with setting ID for PreparedStatement from topic", ex);
        }
    }

    @Override
    public Topic mapRow(ResultSet rs) {
        try {
            return new Topic(rs.getLong("id")
                    , rs.getString("name")
                    , rs.getString("description")
                    , rs.getLong("user_id")
                    , rs.getLong("event_id"),
                    rs.getBoolean("confirm"));
        } catch (SQLException e) {
            log.error("Problem with mapping user", e);
            throw new IllegalStateException(e);
        }
    }


}
