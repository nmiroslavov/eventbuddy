package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.binding.UserUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserRegistrationServiceModel;
import bg.mycompany.eventbuddy.model.service.UserUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.UserAllByRoleViewModel;
import bg.mycompany.eventbuddy.model.view.UserAllEvents;
import bg.mycompany.eventbuddy.model.view.UserByRoleViewModel;
import bg.mycompany.eventbuddy.model.view.UserCurrentDetailsViewModel;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    void initAdminUser();

    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    User findByUsername(String username);

    Optional<User> findByUsernameOptional(String username);

    UserCurrentDetailsViewModel getLoggedInUserViewModel(String userIdentifier);

    UserUpdateBindingModel getUserUpdateBindingModel(String userIdentifier);

    void updateUser(UserUpdateServiceModel userUpdateServiceModel, String userIdentifier) throws IOException;

    UserAllEvents findCurrentUserAllEvents(String username);

    UserAllByRoleViewModel findAllRegularUsers();

    void changeRole(Long userId);

    boolean isAdmin(String username);

    boolean isModerator(User user);

    boolean isModeratorByUsername(String username);
}
