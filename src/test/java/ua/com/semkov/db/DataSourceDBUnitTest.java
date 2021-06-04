package ua.com.semkov.db;

import org.dbunit.DataSourceBasedDBTestCase;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;


import static org.dbunit.Assertion.*;


public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
                "jdbc:h2:mem:conference;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:conference.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("data.xml"));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    public void testGivenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("EVENTS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("EVENTS");
        assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
    }

    @Test
    public void testGivenDataSet_whenInsertEntity_thenTableHasNewEvent() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-event.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("EVENTS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate(
                            "INSERT INTO events (" +
                                    " id, title, description, location, start_time, end_time, organizer_id, status_id) " +
                                    " VALUES (DEFAULT, 'Java1', 'description', 'online', '2021-09-01 10:30:00.000000', '2021-09-01 10:30:00.000000', 1,1)");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "events",
                            "SELECT * FROM EVENTS WHERE title='Java1'");


            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenDataSet_whenInsertEntity_thenTableHasNewUser() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-user.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("USERS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate(
                            "INSERT INTO users (id, login, password, email, first_name, last_name, phone, registration_date, role_id) " +
                                    " VALUES (default, 'univer','univer','university1@gmail.com' ,'Mark' ,'Mark', " +
                                    "'3809627111204', '2022-03-01 11:30:00.000000','1')");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "users",
                            "SELECT * FROM users WHERE email='university1@gmail.com'");


            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenDataSet_whenInsertEntity_thenTableHasNewTopic() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-topic.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("TOPICS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate(
                            "insert into topics (id, name, description, user_id, event_id)" +
                                    "  values (default,'Difference','yes','1','1')");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "topics",
                            "SELECT * FROM topics WHERE name='Difference'");

            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenWhenDeleteEntity_thenTableHasDeletedEvent() throws Exception {

        Connection conn = getDataSource().getConnection();

        conn.createStatement()
                .executeUpdate("INSERT INTO events (" +
                        " id, title, description, location, start_time, end_time, organizer_id, status_id) " +
                        " VALUES (DEFAULT, 'Java1', 'description', 'online', '2021-09-01 10:30:00.000000', '2021-09-01 10:30:00.000000', 1,1)");

        ITable actualDataWithInsertedRow = getConnection()
                .createQueryTable(
                        "events",
                        "SELECT * FROM EVENTS WHERE title='Java1'");

        assertEquals(1, actualDataWithInsertedRow.getRowCount());
        conn.createStatement()
                .executeUpdate("DELETE FROM events where title = 'Java1'");

        ITable actualData = getConnection()
                .createQueryTable(
                        "events",
                        "SELECT * FROM EVENTS WHERE title='Java1'");

        assertEquals(0, actualData.getRowCount());
    }

    @Test
    public void testGivenWhenDeleteEntity_thenTableHasDeletedUser() throws Exception {

        Connection conn = getDataSource().getConnection();

        conn.createStatement()
                .executeUpdate("INSERT INTO users (id, login, password, email, first_name, last_name, phone, registration_date, role_id) " +
                        " VALUES (default, 'univer','univer','university1@gmail.com' ,'Mark' ,'Mark', " +
                        "'3809627111204', '2022-03-01 11:30:00.000000','1') ");
        ITable actualDataWithInsertedRow = getConnection()
                .createQueryTable(
                        "users",
                        "SELECT * FROM USERS WHERE email='university1@gmail.com'");

        assertEquals(1, actualDataWithInsertedRow.getRowCount());
        conn.createStatement()
                .executeUpdate("DELETE FROM users where email='university1@gmail.com'");

        ITable actualData = getConnection()
                .createQueryTable(
                        "users",
                        "SELECT * FROM  users where email='university1@gmail.com'");

        assertEquals(0, actualData.getRowCount());
    }

    @Test
    public void testGivenWhenDeleteEntity_thenTableHasDeletedTopic() throws Exception {

        Connection conn = getDataSource().getConnection();

        conn.createStatement()
                .executeUpdate("insert into topics (id, name, description, user_id, event_id)" +
                        "  values (default,'Difference','yes','1','1');");

        ITable actualDataWithInsertedRow = getConnection()
                .createQueryTable(
                        "topics",
                        "SELECT * FROM topics WHERE name='Difference'");

        assertEquals(1, actualDataWithInsertedRow.getRowCount());
        conn.createStatement()
                .executeUpdate("DELETE FROM topics where name='Difference'");

        ITable actualData = getConnection()
                .createQueryTable(
                        "topics",
                        "SELECT * FROM topics WHERE name='Difference'");

        assertEquals(0, actualData.getRowCount());
    }

    @Test
    public void testGivenDataSet_whenUpdateEntity_thenTableHasUpdatedEvent() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-event-update.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("EVENTS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate("INSERT INTO events (" +
                            " id, title, description, location, start_time, end_time, organizer_id, status_id) " +
                            " VALUES (DEFAULT, 'Java1', 'description', 'online', '2021-09-01 10:30:00.000000', '2021-09-01 10:30:00.000000', 1,1);" +
                            "UPDATE events " +
                            "SET description='newDescription'" +
                            "WHERE title='Java1'");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "events",
                            "SELECT * FROM events WHERE description='newDescription'");

            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenDataSet_whenUpdateEntity_thenTableHasUpdatedUser() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-user-update.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("USERS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate("INSERT INTO users (id, login, password, email, first_name, last_name, phone, registration_date, role_id) " +
                            " VALUES (default, 'univer','univer','university1@gmail.com' ,'Mark' ,'Mark', " +
                            "'3809627111204', '2022-03-01 11:30:00.000000','1'); " +
                            "UPDATE users " +
                            "SET email='newEmail@mail.com'" +
                            "WHERE email='university1@gmail.com'");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "users",
                            "SELECT * FROM users WHERE email='newEmail@mail.com'");

            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenDataSet_whenUpdateEntity_thenTableHasUpdatedTopic() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-topic-update.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("TOPICS");

            Connection conn = getDataSource().getConnection();

            conn.createStatement()
                    .executeUpdate("insert into topics (id, name, description, user_id, event_id)" +
                            "  values (default,'Difference','yes','1','1');" +
                            "UPDATE topics " +
                            "SET name='newName', description='yes', user_id=1, event_id=1 " +
                            "WHERE id=1");
            ITable actualData = getConnection()
                    .createQueryTable(
                            "topics",
                            "SELECT * FROM topics WHERE name='newName'");

            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void testGivenDataSet_whenGetEntityByTitle_thenGetEvent() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("EVENTS");


            ITable actualData = getConnection()
                    .createQueryTable(
                            "events",
                            "SELECT * FROM events where title = 'Java'");

            Object actual = actualData.getValue(0, "title");
            Object expected = expectedTable.getValue(0, "title");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGivenDataSet_whenGetEntityById_thenGetEvent() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("EVENTS");


            ITable actualData = getConnection()
                    .createQueryTable(
                            "events",
                            "SELECT * FROM events where id = 1");

            Object actual = actualData.getValue(0, "title");
            Object expected = expectedTable.getValue(0, "title");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGivenDataSet_whenGetEntityById_thenGetUser() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("USERS");


            ITable actualData = getConnection()
                    .createQueryTable(
                            "users",
                            "SELECT * FROM users where id = 1");

            Object actual = actualData.getValue(0, "email");
            Object expected = expectedTable.getValue(0, "email");

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGivenDataSet_whenGetEntityById_thenGetTopic() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.xml")) {
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("TOPICS");


            ITable actualData = getConnection()
                    .createQueryTable(
                            "events",
                            "SELECT * FROM topics where id = 1");

            Object actual = actualData.getValue(0, "name");
            Object expected = expectedTable.getValue(0, "name");

            assertEquals(expected, actual);
        }
    }


    protected static void printResult(ITable resultTable) throws DataSetException {
        StringBuilder sb = new StringBuilder();
        int columnCount = resultTable.getTableMetaData().getColumns().length;
        String[] columns = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            sb.append(resultTable.getTableMetaData().getColumns()[i].getColumnName());
            sb.append("-");
            sb.append(resultTable.getTableMetaData().getColumns()[i].getDataType());
            sb.append("\t");
            columns[i] = resultTable.getTableMetaData().getColumns()[i].getColumnName();
        }
        sb.append("\n");
        for (int i = 0; i < resultTable.getRowCount(); i++) {
            for (int j = 0; j < columns.length; j++) {
                sb.append(resultTable.getValue(i, columns[j]));
                sb.append("\t");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}