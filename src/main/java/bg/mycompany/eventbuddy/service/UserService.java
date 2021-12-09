package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserRegistrationServiceModel;

public interface UserService {
    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    User findByUsername(String username);
}
