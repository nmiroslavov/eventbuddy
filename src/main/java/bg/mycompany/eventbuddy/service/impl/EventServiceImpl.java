package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Event;
import bg.mycompany.eventbuddy.model.service.EventServiceModel;
import bg.mycompany.eventbuddy.repository.EventRepository;
import bg.mycompany.eventbuddy.sec.CurrentUser;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @Override
    public void createEvent(EventServiceModel eventServiceModel) {
        Event event = modelMapper.map(eventServiceModel, Event.class);
        event.setCreatedOn(LocalDateTime.now());
        event.setCreator(userService.findById(currentUser.getId()));

        eventRepository.save(event);
    }
}
