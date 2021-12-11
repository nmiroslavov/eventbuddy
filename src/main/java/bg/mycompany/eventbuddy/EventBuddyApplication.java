package bg.mycompany.eventbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBuddyApplication.class, args);
    }

}
