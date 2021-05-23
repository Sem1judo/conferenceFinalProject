package ua.com.semkov.db.dto;

import ua.com.semkov.db.entity.Event;
import ua.com.semkov.db.entity.User;

import java.io.Serializable;

public class TopicDto implements Serializable {

    private static final long serialVersionUID = 6216302708905518585L;


    private Long id;
    private String name;
    private String description;
    private User speaker;
    private Event event;

    public TopicDto() {
    }

    public TopicDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TopicDto(Long id, String name, String description, User speaker, Event event) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.speaker = speaker;
        this.event = event;
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

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", speaker=" + speaker +
                ", event=" + event +
                '}';
    }
}

