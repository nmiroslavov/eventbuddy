package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;
import bg.mycompany.eventbuddy.security.SecurityUser;

import java.io.IOException;

public interface EventService {
    void addEvent(EventAddServiceModel eventAddServiceModel) throws IOException;

    EventDetailsViewModel findEventByIdAndReturnView(Long eventId);

    boolean isOwner(String username, Long id);

    EventUpdateBindingModel getEventUpdateBindingModel(Long eventId);

    void updateEvent(EventUpdateServiceModel eventUpdateServiceModel, Long eventId) throws IOException;

    boolean isUserAlreadySignedUpForEvent(SecurityUser user, Long eventId);

    void signUpUser(String userIdentifier, Long eventId);

    boolean isUserSignedUpForEvent(SecurityUser user, Long eventId);

    void signOutUser(String userIdentifier, Long eventId);

    void deleteEvent(Long eventId);
}
