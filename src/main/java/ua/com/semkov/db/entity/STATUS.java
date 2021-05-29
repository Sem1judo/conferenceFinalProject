package ua.com.semkov.db.entity;


import java.io.Serializable;

/**
 * Status entity.
 *
 * @author S.Semkov
 */
public enum STATUS implements Serializable {
    FINISHED, PERFORMING, PLANNED;

    public static STATUS getStatus(Event event) {
        int eventId = Math.toIntExact(event.getStatusId());
        return STATUS.values()[eventId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}