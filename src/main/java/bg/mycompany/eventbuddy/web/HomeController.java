package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.view.UserAllEvents;
import bg.mycompany.eventbuddy.security.SecurityUser;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private final EventService eventService;

    public HomeController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(@AuthenticationPrincipal SecurityUser user, Model model) {

        UserAllEvents userAllEvents = userService.findCurrentUserAllEvents(user.getUserIdentifier());
        model.addAttribute("userAllEvents", userAllEvents);

        return "home";
    }
}
