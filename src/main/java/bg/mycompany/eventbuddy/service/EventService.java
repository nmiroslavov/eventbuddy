package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.service.EventAllServiceModel;
import bg.mycompany.eventbuddy.model.service.EventServiceModel;

public interface EventService {
    void createEvent(EventServiceModel eventServiceModel);

    EventAllServiceModel getAllEvents();
}
