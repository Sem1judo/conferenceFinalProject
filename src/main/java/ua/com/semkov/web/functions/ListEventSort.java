package ua.com.semkov.web.functions;

import ua.com.semkov.db.entity.Event;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;


public final class ListEventSort {

    private static Comparator<Event> compareByStartTime = Comparator.comparing(Event::getStartTime);
    private static Comparator<Event> compareByTopics = new CompareByTopics();


    public static List<Event> sortByTime(List<Event> events) {
        events.sort(compareByStartTime);
        return events;
    }

    public static List<Event> sortByTopics(List<Event> events) {
        events.sort(compareByTopics.reversed());
        return events;
    }

    private static class CompareByTopics implements Comparator<Event>, Serializable {
        private static final long serialVersionUID = -7573485565177593283L;

        public int compare(Event event1, Event event2) {
            return Integer.compare(event1.getTopics().size(), event2.getTopics().size());
        }


    }


}
