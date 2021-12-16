package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.repository.RoleRepository;
import bg.mycompany.eventbuddy.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private Role adminRole;
    private Role userRole;
    private RoleService roleService;

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
        this.roleService = new RoleServiceImpl(this.mockedRoleRepository);
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
}