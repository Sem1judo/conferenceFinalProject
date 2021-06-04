package ua.com.semkov.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import ua.com.semkov.db.DBManager;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.db.dao.DAOProvider;
import ua.com.semkov.db.dao.impl.EventDaoImpl;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class EventServiceImplTest {

    @Mock
    private final EventDaoImpl eventDao = DAOProvider.getInstance().getEventDao();
    @Mock
    private Connection connection;

    @InjectMocks
    private EventServiceImpl eventService;

    public EventServiceImplTest() throws SQLException {
    }


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldGetEvents() throws DAOException, ServiceException {
        List<Event> initialEvents = new ArrayList<>();
        Event testEvent1 = new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description")
                .location("location")
                .build();
        Event testEvent2 = new Event.Builder(
                "title2"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description2")
                .location("location2")
                .build();
        Event testEvent3 = new Event.Builder(
                "title3"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description3")
                .location("location3")
                .build();

        connection = Mockito.mock(Connection.class);

        initialEvents.add(testEvent1);
        initialEvents.add(testEvent2);
        initialEvents.add(testEvent3);

        when(eventDao.getAll()).thenReturn(initialEvents);

        List<Event> faculties = eventService.getEvents();

        assertEquals(3, faculties.size());
        verify(eventDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdGroup() throws DAOException, ServiceException {
        when(eventDao.getById(1L)).thenReturn(new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description")
                .location("location")
                .build());

        Event event = eventService.getEvent(1L);

        assertEquals("title", event.getTitle());
        assertEquals(1, event.getId());
    }

//
//    @Test
//    public void shouldCreateFaculty() {
//        Faculty faculty = new Faculty(1, "Biology", new ArrayList<>(), new ArrayList<>());
//
//        when(facultyDao.create(eq(faculty))).thenReturn(Boolean.TRUE);
//
//        boolean isCreated = facultyServices.create(faculty);
//
//        verify(facultyDao, times(1)).create(faculty);
//        assertTrue(isCreated);
//    }
//
//    @Test
//    public void shouldDeleteFaculty() {
//
//        when(facultyDao.delete(eq(1L))).thenReturn(Boolean.TRUE);
//        boolean isDeleted = facultyServices.delete(1L);
//
//        assertTrue(isDeleted);
//        verify(facultyDao, times(1)).delete(1L);
//    }
//
//    @Test
//    public void shouldUpdateFaculty() {
//        Faculty faculty = new Faculty(1, "Math", new ArrayList<>(), new ArrayList<>());
//
//        when(facultyDao.update(eq(faculty))).thenReturn(Boolean.TRUE);
//
//        boolean isCreated = facultyServices.update(faculty);
//
//        verify(facultyDao, times(1)).update(faculty);
//        assertTrue(isCreated);
//    }
//
//    @Test
//    public void shouldThrowServiceExceptionWhenNameIsNull() {
//        Faculty faculty = new Faculty(1, null, new ArrayList<>(), new ArrayList<>());
//        assertThrows(ServiceException.class, () -> facultyServices.create(faculty));
//    }
//
//    @Test
//    public void shouldThrowServiceExceptionWhenNameTooShort() {
//        Faculty faculty = new Faculty(1, "D", new ArrayList<>(), new ArrayList<>());
//        assertThrows(ServiceException.class, () -> facultyServices.create(faculty));
//    }
//
//    @Test
//    public void shouldThrowServiceExceptionWhenIdZero() {
//        Faculty faculty = new Faculty(0, "Normal", new ArrayList<>(), new ArrayList<>());
//        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
//    }
//
//    @Test
//    public void shouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
//        Faculty faculty = new Faculty(1, "Nam_e"
//                , new ArrayList<>(), new ArrayList<>());
//        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
//    }
//
//    @Test
//    public void shouldThrowServiceExceptionWhenNameIsTooLong() {
//        Faculty faculty = new Faculty(1, "verybignamewhichcantbethereaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//                , new ArrayList<>(), new ArrayList<>());
//        assertThrows(ServiceException.class, () -> facultyServices.update(faculty));
//    }


    public void testGetEvents() {
    }

    public void testGetEvent() {
    }

    public void testCreateEvent() throws DAOException, ServiceException {
        final Event event = new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description")
                .location("location")
                .build();

        given(eventDao.insertEntity(event)).willAnswer(invocation -> invocation.getArgument(0));

        Event added = eventService.createEvent(event);

        assertNotNull(added);

        verify(eventDao.insertEntity(any(Event.class)));
    }

    public void testRemoveEvent() {
    }

    public void testUpdateEvent() {
    }
}