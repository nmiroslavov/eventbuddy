package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.*;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.*;
import bg.mycompany.eventbuddy.repository.EventRepository;
import bg.mycompany.eventbuddy.service.*;
import bg.mycompany.eventbuddy.web.exception.EventNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EventCategoryService eventCategoryService;
    private final PictureService pictureService;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, UserService userService, EventCategoryService eventCategoryService, PictureService pictureService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.eventCategoryService = eventCategoryService;
        this.pictureService = pictureService;
    }

//    @Transactional
    @Override
    public Long addEvent(EventAddServiceModel eventAddServiceModel) throws IOException {
        Event currentEvent = modelMapper.map(eventAddServiceModel, Event.class);
        User creator = userService.findByUsername(eventAddServiceModel.getCreatorUsername());
        EventCategory category = eventCategoryService.findByCategoryEnum(eventAddServiceModel.getCategory());
        currentEvent.setCreator(creator);
        currentEvent.setCategory(category);
        currentEvent.setCreationDateTime(LocalDateTime.now());
        Picture picture = pictureService.createPicture(eventAddServiceModel.getCoverPicture());
        currentEvent.setCoverPicture(picture);

        Event createdEvent = eventRepository.save(currentEvent);
        creator.getHostedAndSignedEvents().add(createdEvent);

        signUpUser(eventAddServiceModel.getCreatorUsername(), createdEvent.getId());

        return createdEvent.getId();
    }

    @Override
    public EventDetailsViewModel findEventByIdAndReturnView(Long eventId, String username) {
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        User currentUser = userService.findByUsername(username);

        EventDetailsViewModel eventDetailsViewModel = modelMapper.map(currentEvent, EventDetailsViewModel.class);
        eventDetailsViewModel.setAttendeesCount(currentEvent.getAttendees().size());
        eventDetailsViewModel.setCoverPictureUrl(currentEvent.getCoverPicture().getUrl());
        eventDetailsViewModel.setCreatorUsername(currentEvent.getCreator().getUsername());
        eventDetailsViewModel.setCategory(currentEvent.getCategory().getCategory().toString());
        eventDetailsViewModel.setStartDate(currentEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        eventDetailsViewModel.setStartTime(currentEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));


        if (isUserSignedUpForEvent(username, eventId)) {
            eventDetailsViewModel.setCanSignUp(true);
            eventDetailsViewModel.setCanSignOut(false);
        } else {
            eventDetailsViewModel.setCanSignUp(false);
            eventDetailsViewModel.setCanSignOut(true);
        }

        if (isOwner(username, eventId) || isEventCreator(username, eventId)) {
            eventDetailsViewModel.setCanDelete(true);
            eventDetailsViewModel.setCanUpdate(true);
            eventDetailsViewModel.setCanSignUp(false);
            eventDetailsViewModel.setCanSignOut(false);
        }

        if (isAdmin(currentUser) && !isEventCreator(username, eventId)) {
            if (isUserSignedUpForEvent(username, eventId)) {
                eventDetailsViewModel.setCanSignUp(true);
                eventDetailsViewModel.setCanSignOut(false);
            } else {
                eventDetailsViewModel.setCanSignUp(false);
                eventDetailsViewModel.setCanSignOut(true);
            }
        }
        if (userService.isModerator(currentUser)) {
            eventDetailsViewModel.setCanUpdate(true);
        }

        return eventDetailsViewModel;
    }

    @Override
    public boolean isOwner(String username, Long id) {

        Optional<Event> currentEvent = eventRepository.findById(id);
        Optional<User> currentUser = userService.findByUsernameOptional(username);

        if (currentEvent.isEmpty() || currentUser.isEmpty()) {
            return false;
        } else {
            Event event = currentEvent.get();
            return isAdmin(currentUser.get()) || event.getCreator().getUsername().equals(username);
        }
    }

    @Override
    public EventUpdateBindingModel getEventUpdateBindingModel(Long eventId) {

        Event currentEvent = eventRepository.findById(eventId).orElseThrow();

        EventUpdateBindingModel eventUpdateBindingModel = modelMapper.map(currentEvent, EventUpdateBindingModel.class);
        eventUpdateBindingModel.setCoverPicture(null);

        return eventUpdateBindingModel;
    }

    @Override
    public void updateEvent(EventUpdateServiceModel eventUpdateServiceModel, Long eventId) throws IOException {
        Event eventToBeUpdated = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        eventToBeUpdated.setName(eventUpdateServiceModel.getName());
        eventToBeUpdated.setDescription(eventUpdateServiceModel.getDescription());
        eventToBeUpdated.setTicketPrice(eventUpdateServiceModel.getTicketPrice());
        EventCategory categoryUpdate = eventCategoryService.findByCategoryEnum(eventUpdateServiceModel.getCategory());
        eventToBeUpdated.setCategory(categoryUpdate);

        if (eventUpdateServiceModel.getStartDateTime() != null) {
            eventToBeUpdated.setCreationDateTime(eventUpdateServiceModel.getStartDateTime());
        }
        if (!eventUpdateServiceModel.getCoverPicture().isEmpty()) {
            Picture updatedCoverPicture = pictureService.updateCoverPicture(eventUpdateServiceModel.getCoverPicture(), eventToBeUpdated.getCoverPicture());
            eventToBeUpdated.setCoverPicture(updatedCoverPicture);
        }

        eventRepository.save(eventToBeUpdated);
    }

    @Override
    public void signUpUser(String userIdentifier, Long eventId) {
        User currentUser = userService.findByUsername(userIdentifier);
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        currentEvent.getAttendees().add(currentUser);
        eventRepository.save(currentEvent);
    }

    @Override
    public boolean isUserSignedUpForEvent(String username, Long eventId) {

        User currentUser = userService.findByUsername(username);
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        return !currentEvent.getAttendees().stream().anyMatch(u -> u.getUsername().equals(currentUser.getUsername()));
    }

//    @Transactional
    @Override
    public void signOutUser(String userIdentifier, Long eventId) {
        User currentUser = userService.findByUsername(userIdentifier);
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        currentEvent.getAttendees().remove(currentUser);
        eventRepository.save(currentEvent);
    }

    @Transactional
    @Override
    public void deleteEvent(Long eventId) {
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        boolean isDeleted = pictureService.deletePicture(currentEvent.getCoverPicture());
        for (User attendee : currentEvent.getAttendees()) {
            attendee.getHostedAndSignedEvents().remove(currentEvent);
        }
        eventRepository.delete(currentEvent);
    }

    @Override
    public void cleanEventsThatHavePassed() {
        eventRepository.deleteAllByPastDateTime();
    }

    @Override
    public EventAllViewModel findAllEvents() {
        EventAllViewModel eventAllViewModel = new EventAllViewModel();
        try {
            List<Event> all = eventRepository.findAll();
            for (Event event : all) {
                EventMiniDetailsViewModel currentEvent = new EventMiniDetailsViewModel();
                currentEvent.setId(event.getId());
                currentEvent.setName(event.getName());
                currentEvent.setTicketPrice(event.getTicketPrice().toString());
                currentEvent.setCategory(event.getCategory().getCategory().toString());
                currentEvent.setStartDate(event.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                currentEvent.setStartTime(event.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                currentEvent.setCoverPictureUrl(event.getCoverPicture().getUrl());
                eventAllViewModel.getAllEvents().add(currentEvent);
            }
        } catch (RuntimeException e) {
            throw new EventNotFoundException(Long.valueOf("1"));

        }
        return eventAllViewModel;
    }

    @Override
    public boolean isEventCreator(String username, Long eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        return event.getCreator().getUsername().equals(username);
    }

    @Override
    public Event findEventById(Long eventId) {

        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    }


    private boolean isAdmin(User user) {
        return user
                .getRoles()
                .stream()
                .map(Role::getRole)
                .anyMatch(r -> r == RoleEnum.ADMIN);
    }
}
