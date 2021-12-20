package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.EventCategory;
import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import bg.mycompany.eventbuddy.repository.EventCategoryRepository;
import bg.mycompany.eventbuddy.service.EventCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EventCategoryServiceImplTest {

    EventCategoryService eventCategoryService;

    @Mock
    private EventCategoryRepository mockedEventCategoryRepository;

    @BeforeEach
    private void init() {
        this.eventCategoryService = new EventCategoryServiceImpl(this.mockedEventCategoryRepository);
    }

    @Test
    public void testInit() {
        Mockito.when(mockedEventCategoryRepository.count()).thenReturn(0L);
        eventCategoryService.initializeEventCategories();
        Assertions.assertTrue(true);
    }

    @Test
    public void testFindCategoryEnum_ShouldReturnValid() {
        EventCategory currentEventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};

        Mockito.when(mockedEventCategoryRepository.findByCategory(EventCategoryEnum.SPORTS)).thenReturn(currentEventCategory);
        Assertions.assertEquals(currentEventCategory, eventCategoryService.findByCategoryEnum(EventCategoryEnum.SPORTS));
    }
}