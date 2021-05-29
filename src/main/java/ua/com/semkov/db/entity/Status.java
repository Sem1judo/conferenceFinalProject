package ua.com.semkov.db.entity;


import java.io.Serializable;

/**
 * Status entity.
 *
 * @author S.Semkov
 */
public enum Status implements Serializable {
    FINISHED, PERFORMING, PLANNED;

    public static Status getStatusStatic(Event event) {
        int eventId = Math.toIntExact(event.getStatusId());
        return Status.values()[eventId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}