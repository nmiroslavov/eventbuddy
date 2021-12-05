package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.repository.RoleRepository;
import bg.mycompany.eventbuddy.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

            roleRepository.saveAll(List.of(admin, user));
        }
    }

    @Override
    public Role findByRole(RoleEnum role) {
        return roleRepository.findRoleByRole(role);
    }


}
