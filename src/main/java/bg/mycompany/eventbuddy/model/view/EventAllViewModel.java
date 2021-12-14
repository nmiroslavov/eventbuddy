package bg.mycompany.eventbuddy.model.view;

import java.util.ArrayList;
import java.util.List;

public class EventAllViewModel {
    private List<EventMiniDetailsViewModel> allEvents = new ArrayList<>();

    public EventAllViewModel() {
    }

    public List<EventMiniDetailsViewModel> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<EventMiniDetailsViewModel> allEvents) {
        this.allEvents = allEvents;
    }
}
