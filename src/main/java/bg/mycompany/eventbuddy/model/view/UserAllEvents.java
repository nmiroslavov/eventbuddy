package bg.mycompany.eventbuddy.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserAllEvents {
    private List<EventMiniDetailsViewModel> hostedEvents = new ArrayList<>();
    private List<EventMiniDetailsViewModel> signedEvents = new ArrayList<>();

    public List<EventMiniDetailsViewModel> getHostedEvents() {
        return hostedEvents;
    }

    public void setHostedEvents(List<EventMiniDetailsViewModel> hostedEvents) {
        this.hostedEvents = hostedEvents;
    }

    public List<EventMiniDetailsViewModel> getSignedEvents() {
        return signedEvents;
    }

    public void setSignedEvents(List<EventMiniDetailsViewModel> signedEvents) {
        this.signedEvents = signedEvents;
    }
}
