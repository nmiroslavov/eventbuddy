package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Event;
import bg.mycompany.eventbuddy.model.entity.EventCategory;
import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.repository.EventRepository;
import bg.mycompany.eventbuddy.service.EventCategoryService;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.PictureService;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

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
}
