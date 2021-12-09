package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.EventAddBindingModel;
import bg.mycompany.eventbuddy.service.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class EventController {

    @ModelAttribute("eventAddBindingModel")
    public EventAddBindingModel eventAddBindingModel() {
        return new EventAddBindingModel();
    }

    @GetMapping("/events/add")
    public String getAddEventPage() {
        return "event-add";
    }

    @PostMapping("/events/add")
    public String addEvent(@Valid EventAddBindingModel eventAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal SecurityUser user) {



        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventAddBindingModel", eventAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventAddBindingModel", bindingResult);
            return "redirect:/events/add";
        }


        return "redirect:/home";
    }
}
