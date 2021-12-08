package bg.mycompany.eventbuddy.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/users/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                          String username,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("badCredentials", true);
        redirectAttributes.addFlashAttribute("username", username);

        return "redirect:/users/login";
    }
}
