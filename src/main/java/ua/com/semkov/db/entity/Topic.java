package ua.com.semkov.db.entity;


import java.io.Serializable;

/**
 * Topic entity.
 *
 * @author S.Semkov
 */
public class Topic  implements Serializable {

    private static final long serialVersionUID = 2386302708905518585L;


    private Long id;
    private String name;
    private String description;
    private Long userId;
    private Long eventId;


    public Topic(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
    }

    public Topic(Long id, String name, String description, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }
    public Topic(String name, String description, Long userId, Long eventId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.eventId = eventId;
    }

    public Topic(Long id, String name, String description, Long userId, Long eventId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.eventId = eventId;
    }



    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", eventId=" + eventId +
                '}';
    }
}
