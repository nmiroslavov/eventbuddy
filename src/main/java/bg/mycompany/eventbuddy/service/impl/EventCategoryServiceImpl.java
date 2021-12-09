package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.EventCategory;
import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import bg.mycompany.eventbuddy.repository.EventCategoryRepository;
import bg.mycompany.eventbuddy.service.EventCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventCategoryServiceImpl implements EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;

    public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
    }

    @Override
    public void initializeEventCategories() {
        if (eventCategoryRepository.count() == 0) {
            List<EventCategory> categories = new ArrayList<>();
            for (EventCategoryEnum categoryEnum : EventCategoryEnum.values()) {
                EventCategory current = new EventCategory();
                current.setCategory(categoryEnum);
                categories.add(current);
            }
            eventCategoryRepository.saveAll(categories);
        }
    }

    @Override
    public EventCategory findByCategoryEnum(EventCategoryEnum categoryEnum) {

        return eventCategoryRepository.findByCategory(categoryEnum);
    }
}
