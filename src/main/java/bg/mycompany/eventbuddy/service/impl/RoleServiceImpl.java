package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.repository.RoleRepository;
import bg.mycompany.eventbuddy.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setRole(RoleEnum.ADMIN);

            Role user = new Role();
            user.setRole(RoleEnum.USER);

            Role moderator = new Role();
            moderator.setRole(RoleEnum.MODERATOR);

            roleRepository.saveAll(List.of(admin, moderator, user));
        }
    }

    @Override
    public Role findByRole(RoleEnum role) {
        return roleRepository.findRoleByRole(role);
    }

    @Override
    public User removeModeratorRole(User user) {
        Role moderatorRole = roleRepository.findRoleByRole(RoleEnum.MODERATOR);
        user.getRoles().remove(moderatorRole);
        return user;
    }

    @Override
    public User addModeratorRole(User user) {

        Role moderatorRole = roleRepository.findRoleByRole(RoleEnum.MODERATOR);
        user.getRoles().add(moderatorRole);
        return user;
    }


}
