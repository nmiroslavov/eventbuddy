package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.*;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;
import bg.mycompany.eventbuddy.repository.EventRepository;
import bg.mycompany.eventbuddy.security.SecurityUser;
import bg.mycompany.eventbuddy.service.EventCategoryService;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.PictureService;
import bg.mycompany.eventbuddy.service.UserService;
import bg.mycompany.eventbuddy.web.exception.EventNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
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

    @Override
    public void addEvent(EventAddServiceModel eventAddServiceModel) throws IOException {
        Event currentEvent = modelMapper.map(eventAddServiceModel, Event.class);
        User creator = userService.findByUsername(eventAddServiceModel.getCreatorUsername());
        EventCategory category = eventCategoryService.findByCategoryEnum(eventAddServiceModel.getCategory());
        currentEvent.setCreator(creator);
        currentEvent.setCategory(category);
        currentEvent.setCreationDateTime(LocalDateTime.now());
        Picture picture = pictureService.createPicture(eventAddServiceModel.getCoverPicture());
        currentEvent.setCoverPicture(picture);

        eventRepository.save(currentEvent);
    }

    @Override
    public EventDetailsViewModel findEventByIdAndReturnView(Long eventId) {
        Event currentEvent = eventRepository.findById(eventId).orElse(null);
        if (currentEvent == null) {
            return null;
        }
        EventDetailsViewModel eventDetailsViewModel = modelMapper.map(currentEvent, EventDetailsViewModel.class);
        eventDetailsViewModel.setAttendeesCount(currentEvent.getAttendees().size());
        eventDetailsViewModel.setCoverPictureUrl(currentEvent.getCoverPicture().getUrl());
        eventDetailsViewModel.setCreatorUsername(currentEvent.getCreator().getUsername());
        eventDetailsViewModel.setCategory(currentEvent.getCategory().getCategory().toString());
        eventDetailsViewModel.setStartDate(currentEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        eventDetailsViewModel.setStartTime(currentEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
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
    public boolean isUserAlreadySignedUpForEvent(SecurityUser user, Long eventId) {

        User currentUser = userService.findByUsername(user.getUsername());
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (currentEvent.getCreator().getUsername().equals(currentUser.getUsername())) {
            return false;
        }

        if (currentEvent.getAttendees().stream().anyMatch(u -> u.getUsername().equals(currentUser.getUsername()))) {
            return false;
        }

        return true;
    }

    @Override
    public void signUpUser(String userIdentifier, Long eventId) {
        User currentUser = userService.findByUsername(userIdentifier);
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        currentEvent.getAttendees().add(currentUser);
        eventRepository.save(currentEvent);
    }

    @Override
    public boolean isUserSignedUpForEvent(SecurityUser user, Long eventId) {

        User currentUser = userService.findByUsername(user.getUsername());
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (currentEvent.getAttendees().stream().anyMatch(u -> u.getUsername().equals(currentUser.getUsername()))) {
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public void signOutUser(String userIdentifier, Long eventId) {
        User currentUser = userService.findByUsername(userIdentifier);
        Event currentEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        currentEvent.getAttendees().remove(currentUser);
        eventRepository.save(currentEvent);
    }


    private boolean isAdmin(User user) {
        return user
                .getRoles()
                .stream()
                .map(Role::getRole)
                .anyMatch(r -> r == RoleEnum.ADMIN);
    }
}
