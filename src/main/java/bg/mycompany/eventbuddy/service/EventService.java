package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;

import java.io.IOException;

public interface EventService {
    void addEvent(EventAddServiceModel eventAddServiceModel) throws IOException;

    EventDetailsViewModel findEventByIdAndReturnView(Long eventId);

    boolean isOwner(String username, Long id);

    EventUpdateBindingModel getEventUpdateBindingModel(Long eventId);

    void updateEvent(EventUpdateServiceModel eventUpdateServiceModel, Long eventId) throws IOException;
}
