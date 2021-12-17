package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.binding.UserUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.view.UserAllByRoleViewModel;
import bg.mycompany.eventbuddy.model.view.UserByRoleViewModel;
import bg.mycompany.eventbuddy.model.view.UserCurrentDetailsViewModel;
import bg.mycompany.eventbuddy.repository.UserRepository;
import bg.mycompany.eventbuddy.security.SecurityUserServiceImpl;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.PictureService;
import bg.mycompany.eventbuddy.service.RoleService;
import bg.mycompany.eventbuddy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User userToTest;
    private UserService userService;
    private Role adminRole;
    private Role userRole;
    private Role moderatorRole;
    private Picture profilePicture;
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private RoleService mockedRoleService;
    @Mock
    private SecurityUserServiceImpl mockedSecurityUserService;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;
    @Mock
    private CloudinaryService mockedCloudinaryService;
    @Mock
    private PictureService mockedPictureService;


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

        this.userToTest = new User() {{
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

        this.userService =
                new UserServiceImpl(this.mockedUserRepository,
                        this.mockedRoleService,
                        this.mockedSecurityUserService,
                        this.modelMapper,
                        this.mockedPasswordEncoder,
                        this.mockedCloudinaryService,
                        this.mockedPictureService);
    }

    @Test
    void findByUsername_ShouldThrow() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername("invalidUsername"));
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        Mockito.when(mockedUserRepository.findByUsername(userToTest.getUsername())).thenReturn(java.util.Optional.ofNullable(userToTest));
        User expectedUser = this.userToTest;
        User actualUser = userService.findByUsername(userToTest.getUsername());
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void isEmailTaken_shouldReturnTrue() {
        Mockito.when(mockedUserRepository.existsByEmail(userToTest.getEmail())).thenReturn(true);
        boolean actual = userService.isEmailTaken(userToTest.getEmail());
        Assertions.assertTrue(actual);
    }

    @Test
    void isEmailTaken_shouldReturnFalse() {
        Mockito.when(mockedUserRepository.existsByEmail(userToTest.getEmail())).thenReturn(false);
        boolean actual = userService.isEmailTaken(userToTest.getEmail());
        Assertions.assertFalse(actual);
    }

    @Test
    void isUsernameTaken_ShouldReturnTrue() {
        Mockito.when(mockedUserRepository.existsByUsername(userToTest.getUsername())).thenReturn(true);
        boolean actual = userService.isUsernameTaken(userToTest.getUsername());
        Assertions.assertTrue(actual);
    }

    @Test
    void isUsernameTaken_ShouldReturnFalse() {
        Mockito.when(mockedUserRepository.existsByUsername(userToTest.getUsername())).thenReturn(false);
        boolean actual = userService.isUsernameTaken(userToTest.getUsername());
        Assertions.assertFalse(actual);
    }

    @Test
    void findByUsernameOptional_ShouldReturnUserNotNull() {
        Mockito.when(userService.findByUsernameOptional(userToTest.getUsername())).thenReturn(Optional.of(userToTest));
        Optional<User> actual = userService.findByUsernameOptional(userToTest.getUsername());
        Assertions.assertEquals(Optional.of(userToTest), actual);
    }

    @Test
    void findByUsernameOptional_ShouldReturnOptionalNull() {
        Optional<User> actual = userService.findByUsernameOptional(userToTest.getUsername());
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void getLoggedInUserViewModel_ShouldReturnViewModel() {
        Mockito.when(mockedUserRepository.findByUsername(userToTest.getUsername())).thenReturn(Optional.of(userToTest));
        UserCurrentDetailsViewModel actual = userService.getLoggedInUserViewModel(userToTest.getUsername());
        Assertions.assertEquals(userToTest.getProfilePicture().getUrl(), actual.getProfilePictureUrl());
        Assertions.assertEquals(userToTest.getProfileCreationDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), actual.getCreatedOn());

    }

    @Test
    void getLoggedInUserViewModel_ShouldThrow() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.getLoggedInUserViewModel("invalid-username"));

    }

    @Test
    void getUserUpdateBindingModel_ShouldReturnBindingModel() {
        Mockito.when(mockedUserRepository.findByUsername(userToTest.getUsername())).thenReturn(Optional.of(userToTest));

        UserUpdateBindingModel expected = modelMapper.map(userToTest, UserUpdateBindingModel.class);

        UserUpdateBindingModel actual = userService.getUserUpdateBindingModel(userToTest.getUsername());

        Assertions.assertEquals(expected.getAge(), actual.getAge());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getLastName(), actual.getLastName());
        Assertions.assertEquals(expected.getPicture(), expected.getPicture());
    }

    @Test
    void getUserUpdateBindingModel_ShouldThrow() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.getUserUpdateBindingModel("invalid-username"));
    }

    @Test
    void isModeratorByUsername_ShouldReturnTrue() {
        Mockito.when(mockedUserRepository.findByUsername(userToTest.getUsername())).thenReturn(Optional.of(userToTest));
        Mockito.when(mockedRoleService.findByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
        boolean result = userService.isModeratorByUsername(userToTest.getUsername());
        Assertions.assertTrue(result);
    }

    @Test
    void isModerator_ShouldReturnTrue() {
        Mockito.when(mockedRoleService.findByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
        boolean result = userService.isModerator(userToTest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAdmin_ShouldReturnTrue() {
        Mockito.when(mockedUserRepository.findByUsername(userToTest.getUsername())).thenReturn(Optional.of(userToTest));
        Mockito.when(mockedRoleService.findByRole(RoleEnum.ADMIN)).thenReturn(adminRole);
        userService.isAdmin(userToTest.getUsername());
    }

    @Test
    void changeRole_AddModerator() {
        Mockito.when(mockedUserRepository.findById(userToTest.getId())).thenReturn(Optional.of(userToTest));
        Mockito.when(mockedRoleService.findByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
        Mockito.when(mockedRoleService.removeModeratorRole(userToTest)).thenReturn(userToTest);
        userService.changeRole(userToTest.getId());
        Assertions.assertTrue(userToTest.getRoles().contains(moderatorRole));

    }

    @Test
    void changeRole_RemoveModerator() {
        userToTest.setRoles(Set.of(userRole));
        Mockito.when(mockedUserRepository.findById(userToTest.getId())).thenReturn(Optional.of(userToTest));
        Mockito.when(mockedRoleService.findByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
        Mockito.when(mockedRoleService.addModeratorRole(userToTest)).thenReturn(userToTest);
        userService.changeRole(userToTest.getId());
        Assertions.assertFalse(userToTest.getRoles().contains(moderatorRole));
    }
}