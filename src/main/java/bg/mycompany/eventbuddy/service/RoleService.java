package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;

public interface RoleService {
    void initializeRoles();
    Role findByRole(RoleEnum role);
}
