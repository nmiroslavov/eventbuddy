package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.EventAddBindingModel;
import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.security.SecurityUser;
import bg.mycompany.eventbuddy.web.exception.EventNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/events/{eventId}/details")
    public String getEventDetails(@PathVariable Long eventId, Model model) {
        EventDetailsViewModel currentEvent = eventService.findEventByIdAndReturnView(eventId);
        if (currentEvent == null) {
            throw new EventNotFoundException(eventId);
        }
        model.addAttribute("event", currentEvent);
        return "event-details";
    }

    @PreAuthorize("isOwner(#eventId)")
    @GetMapping("/events/{eventId}/edit")
    public String getEventEditPage(@PathVariable Long eventId, Model model) {
        EventDetailsViewModel eventDetailsViewModel = eventService.findEventByIdAndReturnView(eventId);
        if (eventDetailsViewModel == null) {
            throw new EventNotFoundException(eventId);
        }

        EventUpdateBindingModel eventUpdateBindingModel = eventService.getEventUpdateBindingModel(eventId);

        model.addAttribute("eventUpdateBindingModel", eventUpdateBindingModel);
        model.addAttribute("eventId", eventId);
        model.addAttribute("categories", EventCategoryEnum.values());
        return "event-update";
    }

    @PreAuthorize("isOwner(#eventId)")
    @GetMapping("/events/{eventId}/edit/errors")
    public String editEventErrors(@PathVariable Long eventId, Model model) {
        model.addAttribute("categories", EventCategoryEnum.values());

        return "event-update";
    }

    @PreAuthorize("isOwner(#eventId)")
    @PatchMapping("/events/{eventId}/edit")
    public String editEvent(@PathVariable Long eventId, @Valid EventUpdateBindingModel eventUpdateBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventUpdateBindingModel", eventUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventUpdateBindingModel", bindingResult);

            return "redirect:/events/" + eventId + "/edit/errors";
        }

        EventUpdateServiceModel eventUpdateServiceModel = modelMapper.map(eventUpdateBindingModel, EventUpdateServiceModel.class);

        eventService.updateEvent(eventUpdateServiceModel, eventId);

        return "redirect:/events/" + eventId + "/details";
    }

    @PreAuthorize("@eventServiceImpl.isUserAlreadySignedUpForEvent(#user, #eventId)")
    @PostMapping("/events/{eventId}/signup")
    public String signUpForEvent(@PathVariable Long eventId, @AuthenticationPrincipal SecurityUser user) {

        eventService.signUpUser(user.getUserIdentifier(), eventId);

       return "redirect:/home";
    }

    @PreAuthorize("@eventServiceImpl.isUserSignedUpForEvent(#user, #eventId)")
    @PostMapping("/events/{eventId}/signout")
    public String signOutOfEvent(@PathVariable Long eventId, @AuthenticationPrincipal SecurityUser user) {

        eventService.signOutUser(user.getUserIdentifier(), eventId);

        return "redirect:/home";

    }
}
