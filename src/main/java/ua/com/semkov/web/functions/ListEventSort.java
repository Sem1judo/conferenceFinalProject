package ua.com.semkov.web.functions;

import ua.com.semkov.db.entity.Event;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;


public final class ListEventSort {

    private static Comparator<Event> compareByStartTime = Comparator.comparing(Event::getStartTime);
    private static Comparator<Event> compareByTopics = new CompareByTopics();
    private static Comparator<Event> compareByUsers = new CompareByUsers();
    private static Comparator<Event> compareById = new CompareById();


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
            case "byUsers":
                events.sort(compareByUsers.reversed());
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
        private static final long serialVersionUID = -7573485565177593283L;

        public int compare(Event event1, Event event2) {
            return Integer.compare(event1.getUsers().size(), event2.getUsers().size());
        }
    }


    private static class CompareById implements Comparator<Event>, Serializable {
        private static final long serialVersionUID = -2573481565177573183L;

        public int compare(Event o1, Event o2) {
            return o1.getId().compareTo(o2.getId());
        }

    }


}
