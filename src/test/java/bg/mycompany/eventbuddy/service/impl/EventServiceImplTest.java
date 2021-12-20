package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.binding.EventAddBindingModel;
import bg.mycompany.eventbuddy.model.binding.EventUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.*;
import bg.mycompany.eventbuddy.model.service.EventAddServiceModel;
import bg.mycompany.eventbuddy.model.service.EventUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.EventDetailsViewModel;
import bg.mycompany.eventbuddy.repository.EventRepository;
import bg.mycompany.eventbuddy.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository mockedEventRepository;
    @Mock
    private UserService mockedUserService;
    @Mock
    private EventCategoryService mockedEventCategoryService;
    @Mock
    private PictureService mockedPictureService;
    @Mock
    private CommentService commentService;

    private ModelMapper modelMapper = new ModelMapper();

    private EventService eventService;

    private User exampleUser;
    private Role adminRole;
    private Role userRole;
    private Role moderatorRole;

    private Picture profilePicture;

    @BeforeEach
    public void init() {
        this.adminRole = new Role();
        this.adminRole.setRole(RoleEnum.ADMIN);

        this.userRole = new Role();
        this.userRole.setRole(RoleEnum.USER);

        this.moderatorRole = new Role();
        this.moderatorRole.setRole(RoleEnum.MODERATOR);

        this.profilePicture = new Picture();
        this.profilePicture.setPublicId("default_id");
        this.profilePicture.setUrl("default_url");

        this.exampleUser = new User() {{
            setId(1L);
            setUsername("john");
            setEmail("john@mail.bg");
            setPassword("password");
            setRoles(Set.of(userRole, adminRole, moderatorRole));
            setFirstName("John");
            setLastName("Doe");
            setAge(21);
            setProfilePicture(profilePicture);
            setHostedAndSignedEvents(new ArrayList<>());
            setProfileCreationDateTime(LocalDateTime.of(2021, 12, 12,12,12));
        }};

        this.eventService = new EventServiceImpl(mockedEventRepository, modelMapper, mockedUserService, mockedEventCategoryService, mockedPictureService);
    }

    @Test
    void testAddEvent() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("title", new byte[0]);
        EventAddServiceModel eventAddServiceModel = new EventAddServiceModel() {{
           setCreatorUsername(exampleUser.getUsername());
           setCategory(EventCategoryEnum.SPORTS);
           setDescription("Example");
           setName("Title");
           setTicketPrice(BigDecimal.valueOf(12L));
           setCoverPicture(multipartFile);
           setStartDateTime(LocalDateTime.now());
        }};
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Mockito.when(mockedUserService.findByUsername(eventAddServiceModel.getCreatorUsername())).thenReturn(exampleUser);
        Mockito.when(mockedEventCategoryService.findByCategoryEnum(eventAddServiceModel.getCategory())).thenReturn(eventCategory);
        Mockito.when(mockedPictureService.createPicture(eventAddServiceModel.getCoverPicture())).thenReturn(profilePicture);

        Event createdEvent = new Event() {{
           setId(1L);
        }};
        Assertions.assertThrows(NullPointerException.class, () -> eventService.addEvent(eventAddServiceModel));
    }

    @Test
    void testFindEventByIdAndReturnView() {
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Event currentEvent = new Event() {{
            setId(1L);
            setName("Title");
            setDescription("Description");
            setCoverPicture(profilePicture);
            setTicketPrice(BigDecimal.valueOf(12L));
            setComments(new ArrayList<>());
            setCategory(eventCategory);
            setCreationDateTime(LocalDateTime.now());
            setCreator(exampleUser);
            setAttendees(List.of(exampleUser));
            setStartDateTime(LocalDateTime.now());
        }};
        Mockito.when(mockedEventRepository.findById(currentEvent.getId())).thenReturn(Optional.of(currentEvent));
        Mockito.when(mockedUserService.findByUsername(exampleUser.getUsername())).thenReturn(exampleUser);
        EventDetailsViewModel result = eventService.findEventByIdAndReturnView(currentEvent.getId(), exampleUser.getUsername());
        Assertions.assertEquals(result.getId(), currentEvent.getId());
    }

    @Test
    void testIsOwner() {
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Event currentEvent = new Event() {{
            setId(1L);
            setName("Title");
            setDescription("Description");
            setCoverPicture(profilePicture);
            setTicketPrice(BigDecimal.valueOf(12L));
            setComments(new ArrayList<>());
            setCategory(eventCategory);
            setCreationDateTime(LocalDateTime.now());
            setCreator(exampleUser);
            setAttendees(List.of(exampleUser));
            setStartDateTime(LocalDateTime.now());
        }};

        Assertions.assertFalse(eventService.isOwner(exampleUser.getUsername(), currentEvent.getId()));
    }

    @Test
    void testGetEventUpdateBindingModel() {
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Event currentEvent = new Event() {{
            setId(1L);
            setName("Title");
            setDescription("Description");
            setCoverPicture(profilePicture);
            setTicketPrice(BigDecimal.valueOf(12L));
            setComments(new ArrayList<>());
            setCategory(eventCategory);
            setCreationDateTime(LocalDateTime.now());
            setCreator(exampleUser);
            setAttendees(List.of(exampleUser));
            setStartDateTime(LocalDateTime.now());
        }};
        Mockito.when(mockedEventRepository.findById(currentEvent.getId())).thenReturn(Optional.of(currentEvent));
        EventUpdateBindingModel result = eventService.getEventUpdateBindingModel(currentEvent.getId());
        Assertions.assertEquals(result.getName(), currentEvent.getName());
    }

    @Test
    void testUpdateEvent() throws IOException {
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Event currentEvent = new Event() {{
            setId(1L);
            setName("Title");
            setDescription("Description");
            setCoverPicture(profilePicture);
            setTicketPrice(BigDecimal.valueOf(12L));
            setComments(new ArrayList<>());
            setCategory(eventCategory);
            setCreationDateTime(LocalDateTime.now());
            setCreator(exampleUser);
            setAttendees(List.of(exampleUser));
            setStartDateTime(LocalDateTime.now());
        }};

        MockMultipartFile file = new MockMultipartFile("title", new byte[0]);
        EventUpdateServiceModel eventUpdateServiceModel = new EventUpdateServiceModel() {{
           setName("updated");
           setCategory(EventCategoryEnum.SPORTS);
           setCoverPicture(file);
           setStartDateTime(null);
        }};
        Mockito.when(mockedEventRepository.findById(currentEvent.getId())).thenReturn(Optional.of(currentEvent));
        Mockito.when(mockedEventCategoryService.findByCategoryEnum(eventUpdateServiceModel.getCategory())).thenReturn(eventCategory);
        eventService.updateEvent(eventUpdateServiceModel, currentEvent.getId());
        Assertions.assertEquals(currentEvent.getName(), eventUpdateServiceModel.getName());
    }

    @Test
    void testSignUpUser() {
        EventCategory eventCategory = new EventCategory() {{
            setId(1L);
            setCategory(EventCategoryEnum.SPORTS);
        }};
        Event currentEvent = new Event() {{
            setId(1L);
            setName("Title");
            setDescription("Description");
            setCoverPicture(profilePicture);
            setTicketPrice(BigDecimal.valueOf(12L));
            setComments(new ArrayList<>());
            setCategory(eventCategory);
            setCreationDateTime(LocalDateTime.now());
            setCreator(exampleUser);
            setAttendees(new ArrayList<>());
            setStartDateTime(LocalDateTime.now());
        }};
        Mockito.when(mockedEventRepository.findById(currentEvent.getId())).thenReturn(Optional.of(currentEvent));
        Mockito.when(mockedUserService.findByUsername(exampleUser.getUsername())).thenReturn(exampleUser);
        eventService.signUpUser(exampleUser.getUsername(), currentEvent.getId());
        assertEquals(1, currentEvent.getAttendees().size());
    }
}