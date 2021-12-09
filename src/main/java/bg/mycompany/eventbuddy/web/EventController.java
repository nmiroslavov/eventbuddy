package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.EventAddBindingModel;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.SecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper;

    public EventController(EventService eventService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

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
                           @AuthenticationPrincipal SecurityUser user) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventAddBindingModel", eventAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventAddBindingModel", bindingResult);
            return "redirect:/events/add";
        }

        EventAddServiceModel eventAddServiceModel = modelMapper.map(eventAddBindingModel, EventAddServiceModel.class);
        eventAddServiceModel.setCreatorUsername(user.getUserIdentifier());

        eventService.addEvent(eventAddServiceModel);

        return "redirect:/home";
    }
}
