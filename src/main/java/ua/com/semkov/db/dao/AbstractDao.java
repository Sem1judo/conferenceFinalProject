package ua.com.semkov.db.dao;


import org.apache.log4j.Logger;
import ua.com.semkov.db.DBManager;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.EntityNotFoundDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDao<K> implements InterfaceDao<K> {


    private static final Logger log = Logger.getLogger(AbstractDao.class.getName());

    private int noOfRecords;

    public abstract String getQueryGetAll();

    public abstract String getQueryGetById();

    public abstract String getQueryInsertEntity();

    public abstract String getQueryDelete();

    public abstract String getQueryUpdateById();

    public abstract String getQueryUpdateSpecificName();

    public abstract String getQueryGetByName();

    public abstract String getQueryGetAllPagination();

    public abstract String getQueryGetTotalCount();

    public abstract void setRowPS(K k, PreparedStatement ps);

    public abstract void setIdPS(K k, PreparedStatement ps);

    public abstract K mapRow(ResultSet rs);

    public abstract K setEntityId(Long id, K k);


    /**
     * Returns list  with entities.
     *
     * @return K entities.
     */

    @Override
    public List<K> getAll() throws DAOException {
        log.debug("Start method getAll()");
        List<K> entities = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(getQueryGetAll())) {

            while (rs.next())
                entities.add(mapRow(rs));
        } catch (SQLException ex) {
            log.error("Cannot obtain a list from the database", ex);
            throw new EntityNotFoundDAOException("Getting list from database failed", ex);
        }
        return entities;
    }

    @Override
    public List<K> getAllPagination(int start, int noOfRecords) throws DAOException {
        log.debug("Start method getAllPagination()");
        ResultSet rs = null;

        List<K> entities = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryGetAllPagination());
             Statement stmt = con.createStatement()) {

            log.trace("start index = " + start + " size of entities = " + noOfRecords);
            ps.setInt(1, start);
            ps.setInt(2, noOfRecords);
            rs = ps.executeQuery();

            while (rs.next())
                entities.add(mapRow(rs));
            rs.close();

            rs = stmt.executeQuery(getQueryGetTotalCount());
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException ex) {
            log.error("Cannot obtain a list from the database", ex);
            throw new EntityNotFoundDAOException("Getting list from database failed", ex);
        } finally {
            DBManager.getInstance().close(rs);
        }

        return entities;
    }


    /**
     * Returns an entity with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     */

    @Override
    public K getById(Long id) throws DAOException {
        ResultSet rs = null;
        K entity = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryGetById())) {

            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                entity = mapRow(rs);
            }
        } catch (SQLException ex) {
            log.error("Getting by id failed", ex);
            throw new EntityNotFoundDAOException("Getting by id failed", ex);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return entity;
    }


    /**
     * Returns an entity with the given login.
     *
     * @param name specific field in entity.
     * @return K entity.
     */

    @Override
    public K getBySpecificName(String name) throws DAOException {
        log.debug("Start method getBySpecificName(String name:" + name + ")");
        ResultSet rs = null;
        K entity = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryGetByName())) {

            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                entity = mapRow(rs);
            }
        } catch (SQLException ex) {
            log.error("Getting by name failed", ex);
            throw new EntityNotFoundDAOException("Getting by name failed", ex);
        } finally {
            DBManager.getInstance().close(rs);
        }
        return entity;
    }


    /**
     * Insert entity.
     *
     * @param K k to insert.
     */
    @Override
    public K insertEntity(K k) throws DAOException {
        log.debug("Start method insertEntity(K k)");

        Connection con = null;
        PreparedStatement ps = null;
        long id;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = con.prepareStatement(getQueryInsertEntity()
                    , Statement.RETURN_GENERATED_KEYS);
            setRowPS(k, ps);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        k = setEntityId(id, k);
                        con.commit();
                        log.trace("Inserted entity --> " + k);
                    }
                } catch (SQLException ex) {
                    DBManager.getInstance().rollback(con);
                    log.error("Creating  failed, no ID obtained.", ex);
                }
            } else {
                DBManager.getInstance().rollback(con);
                log.error("Creating  failed, no rows affected");
            }
        } catch (SQLException ex) {
            log.error("Inserting  failed", ex);
            throw new DAOException("Inserting event failed", ex);
        } finally {
            DBManager.getInstance().close(con, ps);
        }
        return k;
    }


    /**
     * Update entity.
     *
     * @param K k to update.
     */
    @Override
    public boolean updateEntityById(K k) throws DAOException {
        boolean isUpdated;
        log.debug("Start method updateEntityById(K k)");

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.trace("entity ---> " + k);

            ps = con.prepareStatement(getQueryUpdateById());

            setRowPS(k, ps);
            setIdPS(k, ps);

            ps.executeUpdate();
            con.commit();
            isUpdated = true;
        } catch (SQLException ex) {
            if (con != null) {
                DBManager.getInstance().rollback(con);
            }
            log.error("Updating entity failed", ex);
            throw new DAOException("Updating entity failed", ex);

        } finally {
            DBManager.getInstance().close(con, ps);
        }

        return isUpdated;
    }

    /**
     * Update entity.
     *
     * @param K k to update.
     */
    @Override
    public boolean updateEntityBySpecificName(K k) throws DAOException {
        boolean isUpdated;
        log.debug("Start method updateEntityBySpecificName(K k)");
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.trace("entity ---> " + k);

            ps = con.prepareStatement(getQueryUpdateSpecificName());

            ps.executeUpdate();

            setRowPS(k, ps);
            setIdPS(k, ps);

            ps.executeUpdate();

            con.commit();
            isUpdated = true;
        } catch (SQLException ex) {
            if (con != null) {
                DBManager.getInstance().rollback(con);
            }
            log.error("Updating event failed", ex);
            throw new DAOException("Updating event failed", ex);
        } finally {
            DBManager.getInstance().close(con, ps);
        }
        return isUpdated;
    }


    /**
     * Delete entity.
     *
     * @param Long id to delete.
     */
    @Override
    public boolean deleteEntity(Long id) throws DAOException {
        boolean isDeleted;
        log.debug("Start method updateEntityBySpecificName(K k)");

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryDelete())) {

            log.trace("entity ID ---> " + id);
            ps.setLong(1, id);
            ps.executeUpdate();
            isDeleted = true;
        } catch (SQLException ex) {
            log.error("Deleting event failed", ex);
            throw new DAOException("Deleting event failed", ex);
        }
        return isDeleted;
    }

    /**
     * Returns size of list.
     *
     * @return size of list.
     */
    public int getNoOfRecords() {
        return noOfRecords;
    }


}


