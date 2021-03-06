package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.binding.CommentAddBindingModel;
import bg.mycompany.eventbuddy.model.binding.EventAddBindingModel;
import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import bg.mycompany.eventbuddy.model.service.CommentAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventAllViewModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;
import bg.mycompany.eventbuddy.service.CommentService;
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
    private final CommentService commentService;

    public EventController(EventService eventService, ModelMapper modelMapper, CommentService commentService) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
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

        Long eventId = eventService.addEvent(eventAddServiceModel);

        return "redirect:/events/" + eventId + "/details";
    }

    @GetMapping("/events/{eventId}/details/comments")
    public String getEventCommentsPage(@PathVariable Long eventId, Model model) {
        model.addAttribute("eventId", eventId);

        return "event-comments";
    }

    @PostMapping("/events/{eventId}/details/comments")
    public String addComment(@PathVariable Long eventId, @Valid CommentAddBindingModel commentAddBindingModel,
                             BindingResult bindingResult , @AuthenticationPrincipal SecurityUser user,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);

            return "redirect:/events/" + eventId + "/details/comments";
        }

        CommentAddServiceModel commentAddServiceModel = new CommentAddServiceModel();
        commentAddServiceModel.setUsername(user.getUserIdentifier());
        commentAddServiceModel.setTextContent(commentAddBindingModel.getTextContent());
        commentAddServiceModel.setEventId(eventId);

        commentService.createComment(commentAddServiceModel);


        return "redirect:/events/" + eventId + "/details/comments";
    }

    @GetMapping("/events/{eventId}/details")
    public String getEventDetails(@PathVariable Long eventId, Model model, @AuthenticationPrincipal SecurityUser user) {
        EventDetailsViewModel currentEvent = eventService.findEventByIdAndReturnView(eventId, user.getUserIdentifier());
        if (currentEvent == null) {
            throw new EventNotFoundException(eventId);
        }
        model.addAttribute("event", currentEvent);
        return "event-details";
    }

    @PreAuthorize("@eventServiceImpl.isOwner(#user.userIdentifier, #eventId) || @userServiceImpl.isModeratorByUsername(#user.userIdentifier)")
    @GetMapping("/events/{eventId}/edit")
    public String getEventEditPage(@PathVariable Long eventId, Model model, @AuthenticationPrincipal SecurityUser user) {
        EventDetailsViewModel eventDetailsViewModel = eventService.findEventByIdAndReturnView(eventId, user.getUserIdentifier());
        if (eventDetailsViewModel == null) {
            throw new EventNotFoundException(eventId);
        }

        EventUpdateBindingModel eventUpdateBindingModel = eventService.getEventUpdateBindingModel(eventId);

        model.addAttribute("eventUpdateBindingModel", eventUpdateBindingModel);
        model.addAttribute("eventId", eventId);
        model.addAttribute("categories", EventCategoryEnum.values());
        return "event-update";
    }

    @PreAuthorize("@eventServiceImpl.isOwner(#user.userIdentifier, #eventId) || @userServiceImpl.isModeratorByUsername(#user.userIdentifier)")
    @GetMapping("/events/{eventId}/edit/errors")
    public String editEventErrors(@PathVariable Long eventId, Model model, @AuthenticationPrincipal SecurityUser user) {
        model.addAttribute("categories", EventCategoryEnum.values());

        return "event-update";
    }

    @PreAuthorize("@eventServiceImpl.isOwner(#user.userIdentifier, #eventId) || @userServiceImpl.isModeratorByUsername(#user.userIdentifier)")
    @PatchMapping("/events/{eventId}/edit")
    public String editEvent(@PathVariable Long eventId, @Valid EventUpdateBindingModel eventUpdateBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal SecurityUser user) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventUpdateBindingModel", eventUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventUpdateBindingModel", bindingResult);

            return "redirect:/events/" + eventId + "/edit/errors";
        }

        EventUpdateServiceModel eventUpdateServiceModel = modelMapper.map(eventUpdateBindingModel, EventUpdateServiceModel.class);

        eventService.updateEvent(eventUpdateServiceModel, eventId);

        return "redirect:/events/" + eventId + "/details";
    }

    @PreAuthorize("@eventServiceImpl.isUserSignedUpForEvent(#user.getUserIdentifier(), #eventId)")
    @PostMapping("/events/{eventId}/signup")
    public String signUpForEvent(@PathVariable Long eventId, @AuthenticationPrincipal SecurityUser user) {

        eventService.signUpUser(user.getUserIdentifier(), eventId);

       return "redirect:/home";
    }

    @PreAuthorize("!@eventServiceImpl.isUserSignedUpForEvent(#user.getUserIdentifier(), #eventId)")
    @PostMapping("/events/{eventId}/signout")
    public String signOutOfEvent(@PathVariable Long eventId, @AuthenticationPrincipal SecurityUser user) {

        eventService.signOutUser(user.getUserIdentifier(), eventId);

        return "redirect:/home";

    }

    @PreAuthorize("isOwner(#eventId)")
    @DeleteMapping("/events/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {

        eventService.deleteEvent(eventId);

        return "redirect:/home";
    }

    @GetMapping("/events/explore")
    public String getAllEvents(Model model) {
        EventAllViewModel allEvents = eventService.findAllEvents();
        model.addAttribute("allEvents", allEvents);

        return "events-explore";
    }

}
