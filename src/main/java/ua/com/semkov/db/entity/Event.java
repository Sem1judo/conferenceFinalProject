package ua.com.semkov.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Event entity.
 *
 * @author S.Semkov
 */
public class Event implements Serializable {

    private static final long serialVersionUID = -6889036215149495688L;

    private Long id;
    private final String title;
    private final String description;
    private final String location;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Long organizerId;
    private final Long statusId;
    private final List<Topic> topics;
    private final List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public List<User> getUsers() {
        return users;
    }

    public static class Builder {
        private final String title;
        private final LocalDateTime startTime;
        private final LocalDateTime endTime;
        private final Long organizerId;


        public Builder(String title, LocalDateTime startTime, LocalDateTime endTime, Long organizerId) {
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.organizerId = organizerId;
        }


        private Long id;
        private String description = "Exhilarating event";
        private String location = "online";
        private Long statusId = 2L;
        private List<Topic> topics;
        private List<User> users;


        public Builder statusId(Long statusIdVal) {
            statusId = statusIdVal;
            return this;
        }

        public Builder id(Long idVal) {
            id = idVal;
            return this;
        }

        public Builder topics(List<Topic> topicsVal) {
            topics = topicsVal;
            return this;
        }

        public Builder users(List<User> usersVal) {
            users = usersVal;
            return this;
        }

        public Builder description(String descriptionVal) {
            description = descriptionVal;
            return this;
        }

        public Builder location(String locationVal) {
            location = locationVal;
            return this;
        }


        public Event build() {
            return new Event(this);
        }
    }

    private Event(Builder builder) {
        id = builder.id;
        title = builder.title;
        startTime = builder.startTime;
        endTime = builder.endTime;
        organizerId = builder.organizerId;
        statusId = builder.statusId;
        description = builder.description;
        location = builder.location;
        topics = builder.topics;
        users = builder.users;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", organizerId=" + organizerId +
                ", statusId=" + statusId +
                ", topics=" + topics +
                ", users=" + users +
                '}';
    }
}
