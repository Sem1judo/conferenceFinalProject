package ua.com.semkov.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB manager. Works with PostgreSQL.
 * Only the required DAO methods are defined!
 *
 * @author S.Semkov
 */
public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class);


    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // conference - the name of data source
            DataSource ds = (DataSource) envContext.lookup("jdbc/conference");
            con = ds.getConnection();
        } catch (NamingException ex) {
            log.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }

    private DBManager() {
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException e) {
            log.error("Attempting to close closed connection", e);
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            log.error("ResultSet close error!", e);
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            log.error("Statement close error!", e);
        }
    }

    public void close(Connection con, Statement st) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException e) {
            log.error("Attempting to close closed connection", e);
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            log.error("Statement close error!", e);
        }
    }

    public void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public int executeQuery(String query) throws ClassNotFoundException, SQLException {
        return DBManager.getInstance().getConnection().createStatement().executeUpdate(query);
    }

}