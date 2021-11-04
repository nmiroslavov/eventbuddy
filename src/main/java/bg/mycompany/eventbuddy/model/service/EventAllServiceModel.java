package bg.mycompany.eventbuddy.model.service;

import bg.mycompany.eventbuddy.model.entity.Event;

import java.util.List;

public class EventAllServiceModel {
    List<Event> events;

    public EventAllServiceModel() {
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
