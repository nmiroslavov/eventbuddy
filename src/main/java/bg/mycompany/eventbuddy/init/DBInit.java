package bg.mycompany.eventbuddy.init;

import bg.mycompany.eventbuddy.service.RoleService;
import bg.mycompany.eventbuddy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;

    public DBInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.initializeRoles();
        userService.registerAdmin();
    }
}
