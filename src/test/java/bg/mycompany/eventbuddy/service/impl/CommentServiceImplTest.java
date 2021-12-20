package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.*;
import bg.mycompany.eventbuddy.model.service.CommentAddServiceModel;
import bg.mycompany.eventbuddy.model.view.CommentForEventViewModel;
import bg.mycompany.eventbuddy.repository.CommentRepository;
import bg.mycompany.eventbuddy.service.CommentService;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private ModelMapper modelMapper = new ModelMapper();
    private CommentService commentService;
    private User exampleUser;
    private Event exampleEvent;
    private Role adminRole;
    private Role userRole;
    private Role moderatorRole;
    private Picture profilePicture;

    @Mock
    private CommentRepository mockedCommentRepository;
    @Mock
    private EventService mockedEventService;
    @Mock
    private UserService mockedUserService;

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
            setUsername("test");
            setEmail("test@mail.bg");
            setPassword("password");
            setRoles(Set.of(userRole, adminRole, moderatorRole));
            setFirstName("John");
            setLastName("Doe");
            setAge(21);
            setProfilePicture(profilePicture);
            setHostedAndSignedEvents(new ArrayList<>());
            setProfileCreationDateTime(LocalDateTime.of(2021, 12, 12,12,12));
        }};

        this.exampleEvent = new Event() {{
            setId(1L);
            setCreator(exampleUser);
            setCategory(new EventCategory() {{
                setId(1L);
                setCategory(EventCategoryEnum.CONCERT);
            }});
            setTicketPrice(BigDecimal.valueOf(12));
            setName("Example event");
            setDescription("Example description");
            setCoverPicture(new Picture());
            setStartDateTime(LocalDateTime.now());
            setAttendees(List.of(exampleUser));
            setComments(new ArrayList<>());
            setCreationDateTime(LocalDateTime.now());
        }};

        this.commentService = new CommentServiceImpl(mockedCommentRepository, mockedEventService, modelMapper, mockedUserService);
    }

    @Test
    void getCommentsForEvent_ShouldReturnEmpty() {
        Mockito.when(mockedEventService.findEventById(exampleEvent.getId())).thenReturn(exampleEvent);
        List<CommentForEventViewModel> actual = commentService.getCommentsForEvent(exampleEvent.getId());
        Assertions.assertTrue(actual.isEmpty());
    }


    @Test
    void testCreateComment() {
        Comment comment = new Comment() {{
           setId(1L);
           setCreatedDateTime(LocalDateTime.now());
        }};
        CommentAddServiceModel commentAddServiceModel = new CommentAddServiceModel() {{
           setEventId(exampleEvent.getId());
           setTextContent("Example comment");
           setUsername(exampleUser.getUsername());
        }};
        Mockito.when(mockedEventService.findEventById(exampleEvent.getId())).thenReturn(exampleEvent);
        Mockito.when(mockedUserService.findByUsername(exampleUser.getUsername())).thenReturn(exampleUser);
        Assertions.assertThrows(NullPointerException.class, () -> commentService.createComment(commentAddServiceModel));
    }

    @Test
    void testMapCommentToViewModel() {
        Comment currentComment = new Comment() {{
            setId(1L);
            setEvent(exampleEvent);
            setAuthor(exampleUser);
            setTextContent("Example comment");
            setCreatedDateTime(LocalDateTime.now());
        }};
        CommentForEventViewModel result = commentService.mapCommentToViewModel(currentComment);
        Assertions.assertEquals(currentComment.getId(), result.getCommentId());
    }
}