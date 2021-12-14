package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventAllViewModel;
import bg.mycompany.eventbuddy.model.view.EventAttendeesViewModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;

import java.io.IOException;

public interface EventService {
    Long addEvent(EventAddServiceModel eventAddServiceModel) throws IOException;

    EventDetailsViewModel findEventByIdAndReturnView(Long eventId, String username);

    boolean isOwner(String username, Long id);

    EventUpdateBindingModel getEventUpdateBindingModel(Long eventId);

    void updateEvent(EventUpdateServiceModel eventUpdateServiceModel, Long eventId) throws IOException;

    void signUpUser(String userIdentifier, Long eventId);

    boolean isUserSignedUpForEvent(String username, Long eventId);

    void signOutUser(String userIdentifier, Long eventId);

    void deleteEvent(Long eventId);

    EventAttendeesViewModel getEventAttendees(Long eventId);

    void cleanEventsThatHavePassed();

    EventAllViewModel findAllEvents();

    boolean isEventCreator(String username, Long eventId);
}
