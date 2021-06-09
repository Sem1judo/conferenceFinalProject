package ua.com.semkov.service.impl;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.util.ReflectionTestUtils;
import ua.com.semkov.db.dao.AbstractDao;
import ua.com.semkov.db.entity.Event;
import ua.com.semkov.exceptions.DAOException;
import ua.com.semkov.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @Mock
    private AbstractDao<Event> eventDao;

    @InjectMocks
    private EventServiceImpl eventService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventDao", eventDao);
    }

    @Test
    public void testFindById() throws DAOException, ServiceException {
        eventService.getEvent(1L);
        Mockito.verify(eventDao).getById(1L);
    }


    @Test
    public void shouldGetAllEventsWhenDataExist() throws DAOException, ServiceException {
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


        initialEvents.add(testEvent1);
        initialEvents.add(testEvent2);
        initialEvents.add(testEvent3);

        when(eventDao.getAll()).thenReturn(initialEvents);

        List<Event> faculties = eventService.getEvents();

        assertEquals(3, faculties.size());
        verify(eventDao, times(1)).getAll();
    }

    @Test
    public void shouldGetByIdEventWhenEventExist() throws DAOException, ServiceException {
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
        assertEquals("location", event.getLocation());
        assertEquals("description", event.getDescription());
    }

    @Test
    public void shouldCreateEventWhenValidEvent() throws DAOException, ServiceException {
        Event expectedEvent = new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description")
                .location("location")
                .build();

        when(eventDao.insertEntity(eq(expectedEvent))).thenReturn(expectedEvent);

        Event actualEvent = eventService.createEvent(expectedEvent);

        verify(eventDao, times(1)).insertEntity(expectedEvent);
        assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        assertEquals(expectedEvent.getLocation(), actualEvent.getLocation());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
    }

    @Test
    public void shouldDeleteEventWhenExist() throws DAOException, ServiceException {

        when(eventDao.deleteEntity(eq(1L))).thenReturn(Boolean.TRUE);
        boolean isDeleted = eventService.removeEvent(1L);

        assertTrue(isDeleted);
        verify(eventDao, times(1)).deleteEntity(1L);
    }

    @Test
    public void shouldUpdateEventWhenExist() throws DAOException, ServiceException {
        Event expectedEvent = new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();

        when(eventDao.updateEntityById(eq(expectedEvent))).thenReturn(Boolean.TRUE);

        boolean isCreated = eventService.updateEvent(expectedEvent);

        verify(eventDao, times(1)).updateEntityById(expectedEvent);
        assertTrue(isCreated);
    }

    @Test
    public void shouldThrowServiceExceptionWhenTitleIsNull() {
        Event expectedEvent = new Event.Builder(
                null
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();

        assertThrows(ServiceException.class, () -> eventService.createEvent(expectedEvent));
    }

    @Test
    public void shouldThrowServiceExceptionWhenTitleIsEmpty() {
        Event expectedEvent = new Event.Builder(
                ""
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();

        assertThrows(ServiceException.class, () -> eventService.createEvent(expectedEvent));
    }

    @Test
    public void shouldThrowServiceExceptionWhenDescriptionTooShort() {
        Event expectedEvent = new Event.Builder(
                "title"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();
        assertThrows(ServiceException.class, () -> eventService.createEvent(expectedEvent));
    }



    @Test
    public void shouldThrowServiceExceptionWhenTitleHaveForbiddenSymbol() {
        Event expectedEvent = new Event.Builder(
                "title%^$@"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();

        assertThrows(ServiceException.class, () -> eventService.updateEvent(expectedEvent));
    }

    @Test
    public void shouldThrowServiceExceptionWhenTitleIsTooLong() {
        Event expectedEvent = new Event.Builder(
                "MORE SYMBOLS THAN HAVE TO BE = MORE SYMBOLS THAN HAVE TO BE" +
                        "MORE SYMBOLS THAN HAVE TO BE = MORE SYMBOLS THAN HAVE TO BE" +
                        "MORE SYMBOLS THAN HAVE TO BE MORE SYMBOLS THAN HAVE TO BE" +
                        "MORE SYMBOLS THAN HAVE TO BEMORE SYMBOLS THAN HAVE TO BE" +
                        "MORE SYMBOLS THAN HAVE TO BE MORE SYMBOLS THAN HAVE TO BE" +
                        "MORE SYMBOLS THAN HAVE TO BE MORE SYMBOLS THAN HAVE TO BE"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , 1L)
                .id(1L)
                .statusId(1L)
                .description("description the best")
                .location("location")
                .build();

        assertThrows(ServiceException.class, () -> eventService.updateEvent(expectedEvent));

    }


}