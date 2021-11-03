package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserServiceModel;

import java.util.Optional;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    boolean checkUsernameIsTaken(String username);

    boolean checkEmailIsTaken(String email);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(String username, Long id);

    User findById(Long id);
}
