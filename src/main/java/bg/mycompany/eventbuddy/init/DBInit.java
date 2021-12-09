package bg.mycompany.eventbuddy.init;

import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final CloudinaryService cloudinaryService;

    public DBInit(RoleService roleService, CloudinaryService cloudinaryService) {
        this.roleService = roleService;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.initializeRoles();
        cloudinaryService.defaultProfile();
    }
}
