package ua.com.semkov.db.dao;


import org.apache.log4j.Logger;
import ua.com.semkov.db.DBManager;
import ua.com.semkov.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDao<K> implements InterfaceDao<K> {

    private int noOfRecords;

    private static final Logger log = Logger.getLogger(AbstractDao.class.getName());

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


    /**
     * Returns list  with entities.
     *
     * @return K entities.
     */

    @Override
    public List<K> getAll() throws DAOException {
        List<K> entities = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(getQueryGetAll())) {

            while (rs.next())
                entities.add(mapRow(rs));
        } catch (SQLException ex) {
            log.error("Cannot obtain a list from the database", ex);
            throw new DAOException("Getting list from database failed", ex);
        }
        return entities;
    }

    @Override
    public List<K> getAllPagination(int start, int noOfRecords) throws DAOException {
        ResultSet rs = null;

        List<K> entities = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryGetAllPagination());
             Statement stmt = con.createStatement()) {

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
            throw new DAOException("Getting list from database failed", ex);
        } finally {
            close(rs);
        }

        return entities;
    }


    public int getNoOfRecords() {
        return noOfRecords;
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
            log.trace("entered event id ---> " + id);

            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                entity = mapRow(rs);
            }
        } catch (SQLException ex) {
            log.error("Getting by id failed", ex);
            throw new DAOException("Getting by id failed", ex);
        } finally {
            close(rs);
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
            throw new DAOException("Getting by name failed", ex);
        } finally {
            close(rs);
        }
        return entity;
    }


    /**
     * Insert entity.
     *
     * @param K k to insert.
     */
    @Override
    public int insertEntityReturningId(K k) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
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
                        id = rs.getInt(1);
                        con.commit();
                    }
                } catch (SQLException ex) {
                    rollback(con);
                    log.error("Creating  failed, no ID obtained.", ex);
                }

            } else {
                rollback(con);
                log.error("Creating  failed, no rows affected");
            }
        } catch (SQLException ex) {
            log.error("Inserting  failed", ex);
            throw new DAOException("Inserting event failed", ex);
        } finally {
            close(ps);
            close(con);
        }
        return id;
    }


    /**
     * Update entity.
     *
     * @param K k to update.
     */
    @Override
    public void updateEntityById(K k) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = con.prepareStatement(getQueryUpdateById());

            setRowPS(k, ps);
            setIdPS(k, ps);

            ps.executeUpdate();

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            log.error("Updating event failed", ex);
            throw new DAOException("Updating event failed", ex);
        } finally {
            close(ps);
            close(con);
        }
    }

    /**
     * Update entity.
     *
     * @param K k to update.
     */
    @Override
    public void updateEntityBySpecificName(K k) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            ps = con.prepareStatement(getQueryUpdateSpecificName());

            int rowsAdded = ps.executeUpdate();

            setRowPS(k, ps);
            setIdPS(k, ps);

            if (rowsAdded > 0) {
                con.commit();
            } else throw new SQLException("updated entity is null");
        } catch (SQLException ex) {
            rollback(con);
            log.error("Updating event failed", ex);
            throw new DAOException("Updating event failed", ex);
        } finally {
            close(ps);
            close(con);
        }
    }


    /**
     * Delete entity.
     *
     * @param Long id to delete.
     */
    @Override
    public void deleteEntity(Long id) throws DAOException {

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(getQueryDelete())) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            log.error("Deleting event failed", ex);
            throw new DAOException("Deleting event failed", ex);
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

    public void rollback(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
