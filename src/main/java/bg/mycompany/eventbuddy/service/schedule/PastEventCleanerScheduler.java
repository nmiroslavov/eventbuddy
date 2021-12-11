package bg.mycompany.eventbuddy.service.schedule;

import bg.mycompany.eventbuddy.service.EventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PastEventCleanerScheduler {

    private final EventService eventService;

    public PastEventCleanerScheduler(EventService eventService) {
        this.eventService = eventService;
    }

    @Scheduled(cron = "* 0 0 * * *")
    public void cleanEventsThatHavePassed() {
        eventService.cleanEventsThatHavePassed();
    }
}
