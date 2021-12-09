package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.EventCategory;
import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;

public interface EventCategoryService {
    void initializeEventCategories();

    EventCategory findByCategoryEnum(EventCategoryEnum categoryEnum);
}
