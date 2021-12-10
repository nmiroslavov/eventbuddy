package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.security.SecurityUser;
import bg.mycompany.eventbuddy.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(@AuthenticationPrincipal SecurityUser user, Model model) {
        User currentUser = userService.findByUsername(user.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "home";
    }
}
