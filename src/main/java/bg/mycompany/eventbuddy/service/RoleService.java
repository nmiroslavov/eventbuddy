package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;

public interface RoleService {
    void initializeRoles();
    Role findByRole(RoleEnum role);

    User removeModeratorRole(User user);

    User addModeratorRole(User user);
}
