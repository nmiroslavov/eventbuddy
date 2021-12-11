package bg.mycompany.eventbuddy.model.view;

import java.util.ArrayList;
import java.util.List;

public class EventAttendeesViewModel {
    List<EventAttendeeViewModel> attendees = new ArrayList<>();

    public EventAttendeesViewModel() {
    }

    public List<EventAttendeeViewModel> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<EventAttendeeViewModel> attendees) {
        this.attendees = attendees;
    }
}
