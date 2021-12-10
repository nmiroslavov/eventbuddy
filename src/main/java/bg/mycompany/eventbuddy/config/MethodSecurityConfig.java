package bg.mycompany.eventbuddy.config;

import bg.mycompany.eventbuddy.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private EventBuddyMethodSecurityExpressionHandler eventBuddyMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return eventBuddyMethodSecurityExpressionHandler;
    }

    @Bean
    public EventBuddyMethodSecurityExpressionHandler createExpressionHandler(EventService eventService) {
       return new EventBuddyMethodSecurityExpressionHandler(eventService);
    }

}
