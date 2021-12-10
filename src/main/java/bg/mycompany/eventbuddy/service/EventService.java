package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;

import java.io.IOException;

public interface EventService {
    void addEvent(EventAddServiceModel eventAddServiceModel) throws IOException;

    EventDetailsViewModel findEventByIdAndReturnView(Long eventId);
}
