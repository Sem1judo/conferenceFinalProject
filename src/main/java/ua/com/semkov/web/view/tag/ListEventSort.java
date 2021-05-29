package ua.com.semkov.web.view.tag;

import ua.com.semkov.db.entity.Event;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;


public final class ListEventSort {

    private static final Comparator<Event> compareByStartTime = Comparator.comparing(Event::getStartTime);
    private static final Comparator<Event> compareByTopics = new CompareByTopics();
    private static final Comparator<Event> compareByUsers = new CompareByUsers();
    private static final Comparator<Event> compareById = Comparator.comparing(Event::getId);
    private static final Comparator<Event> compareByStatus = Comparator.comparing(Event::getStatusId).reversed();


    public static List<Event> sortByTime(List<Event> events) {
        events.sort(compareByStartTime);
        return events;
    }

    public static List<Event> sortBy(List<Event> events, String sortBy) {
        switch (sortBy) {
            case "byStartTime":
                events.sort(compareByStartTime);
                break;
            case "byTopics":
                events.sort(compareByTopics.reversed());
                break;
            case "byUsers":
                events.sort(compareByUsers.reversed());
                break;
            case "byStatus":
                events.sort(compareByStatus);
                break;
            default:
                events.sort(compareById);
                break;
        }
        return events;
    }

    private static class CompareByTopics implements Comparator<Event>, Serializable {
        private static final long serialVersionUID = -7573485565177593283L;

        public int compare(Event event1, Event event2) {
            return Integer.compare(event1.getTopics().size(), event2.getTopics().size());
        }
    }

    private static class CompareByUsers implements Comparator<Event>, Serializable {
        private static final long serialVersionUID = -6573485565177593283L;

        public int compare(Event event1, Event event2) {
            return Integer.compare(event1.getUsers().size(), event2.getUsers().size());
        }
    }


}
