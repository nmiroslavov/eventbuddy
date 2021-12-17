package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.repository.RoleRepository;
import bg.mycompany.eventbuddy.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private Role adminRole;
    private Role userRole;
    private Role moderatorRole;
    private RoleService roleService;
    private Picture profilePicture;
    private User user;

    @Mock
    private RoleRepository mockedRoleRepository;

    @BeforeEach
    public void init() {
        this.adminRole = new Role() {{
            setId(1L);
            setRole(RoleEnum.ADMIN);
        }};
        this.userRole = new Role() {{
            setId(2L);
            setRole(RoleEnum.USER);
        }};
        this.moderatorRole = new Role() {{
            setId(3L);
            setRole(RoleEnum.MODERATOR);
        }};

        this.roleService = new RoleServiceImpl(this.mockedRoleRepository);

        this.profilePicture = new Picture();
        this.profilePicture.setPublicId("default_id");
        this.profilePicture.setUrl("default_url");

        this.user = new User() {{
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
    }

    @Test
    void returnAdminRole() {
        // Arrange
        Mockito.when(this.mockedRoleRepository.findRoleByRole(RoleEnum.ADMIN)).thenReturn(this.adminRole);

        Role expectedAdminRole = this.adminRole;

        // Act
        Role actualAdminRole = roleService.findByRole(RoleEnum.ADMIN);

        // Assert
        Assertions.assertEquals(expectedAdminRole, actualAdminRole);

    }

    @Test
    void returnUserRole() {
        Mockito.when(this.mockedRoleRepository.findRoleByRole(RoleEnum.USER)).thenReturn(this.userRole);
        Role expectedUserRole = this.userRole;

        Role actualUserRole = roleService.findByRole(RoleEnum.USER);

        Assertions.assertEquals(expectedUserRole, actualUserRole);
    }

//    @Test
//    void removeModeratorRole_ShouldReturnUser() {
//        Mockito.when(mockedRoleRepository.findRoleByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
//        User result = roleService.removeModeratorRole(user);
//        Assertions.assertFalse(result.getRoles().contains(moderatorRole));
//    }
//
//    @Test
//    void addModeratorRole_ShouldReturnUser() {
//        user.setRoles(Set.of(adminRole, userRole));
//        Mockito.when(mockedRoleRepository.findRoleByRole(RoleEnum.MODERATOR)).thenReturn(moderatorRole);
//        User updatedUser = roleService.addModeratorRole(this.user);
//        Assertions.assertTrue(updatedUser.getRoles().contains(moderatorRole));
//    }
}