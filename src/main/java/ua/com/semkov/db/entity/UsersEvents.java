package ua.com.semkov.db.entity;

import java.io.Serializable;

public class UsersEvents implements Serializable {

    private static final long serialVersionUID = -6889036256149495388L;
    private Long userId;
    private Long eventId;

    public UsersEvents() {
    }

    public UsersEvents(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "userId=" + userId +
                ", eventId=" + eventId +
                '}';
    }
}
