package bg.mycompany.eventbuddy.init;

import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.EventCategoryService;
import bg.mycompany.eventbuddy.service.RoleService;
import bg.mycompany.eventbuddy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final CloudinaryService cloudinaryService;
    private final EventCategoryService eventCategoryService;
    private final UserService userService;

    public DBInit(RoleService roleService, CloudinaryService cloudinaryService, EventCategoryService eventCategoryService, UserService userService) {
        this.roleService = roleService;
        this.cloudinaryService = cloudinaryService;
        this.eventCategoryService = eventCategoryService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.initializeRoles();
        cloudinaryService.defaultProfile();
        eventCategoryService.initializeEventCategories();
        userService.initAdminUser();
    }
}
