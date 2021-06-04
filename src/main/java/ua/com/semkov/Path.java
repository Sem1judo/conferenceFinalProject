package ua.com.semkov;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author S.Semkov
 */
public final class Path {

    //redirect
    public static final String REDIRECT = "redirect:";
    public static final String PAGE__EVENT_EDIT = "jsp/admin/eventEdit.jsp";
    public static final String PAGE__TOPIC_EDIT = "jsp/admin/topicEdit.jsp";

    // pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__ERROR_PAGE_404 = "jsp/error/error_page404.jsp";
    public static final String PAGE__ERROR_PAGE_505 = "jsp/error/error_page500.jsp";
    public static final String PAGE__LIST_EVENTS = "jsp/admin/list_events.jsp";
    public static final String PAGE__LIST_TOPICS = "jsp/admin/list_topics.jsp";
    public static final String PAGE__SETTINGS = "jsp/settings.jsp";
    public static final String PAGE__PROFILE_USER = "jsp/speaker_client/profile.jsp";


    // commands
    public static final String COMMAND_LIST_EVENTS = "controller?command=listEvents";
    public static final String COMMAND__LIST_TOPICS = "controller?command=listTopics";
    public static final String COMMAND__PROFILE_LIST_EVENTS = "controller?command=profileListEvents";
    public static final String COMMAND__UPDATE_EVENT = "controller?command=updateEvent";


}