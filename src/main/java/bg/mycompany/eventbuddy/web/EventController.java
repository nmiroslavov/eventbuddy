package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.EventCreateBindingModel;
import bg.mycompany.eventbuddy.model.service.EventAllServiceModel;
import bg.mycompany.eventbuddy.model.service.EventServiceModel;
import bg.mycompany.eventbuddy.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper;

    public EventController(EventService eventService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public EventCreateBindingModel eventCreateBindingModel() {
        return new EventCreateBindingModel();
    }

    @GetMapping("/create")
    public String create() {
        return "add-event";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid EventCreateBindingModel eventCreateBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventCreateBindingModel", eventCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventCreateBindingModel", bindingResult);

            return "redirect:create";
        }

        eventService.createEvent(modelMapper.map(eventCreateBindingModel, EventServiceModel.class));

        return "redirect:/home";
    }

    @GetMapping("/explore")
    public String explore(Model model) {
        EventAllServiceModel events = eventService.getAllEvents();
        model.addAttribute("events", events);

        return "explore";
    }
}
