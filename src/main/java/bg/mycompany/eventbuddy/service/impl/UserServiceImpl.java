package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserServiceModel;
import bg.mycompany.eventbuddy.repository.UserRepository;
import bg.mycompany.eventbuddy.sec.CurrentUser;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, CurrentUser currentUser) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User userRegistration = modelMapper.map(userServiceModel, User.class);
        userRegistration.setAge("18");
        userRegistration.setRegisteredOn(LocalDateTime.now());

        userRepository.save(userRegistration);
    }

    @Override
    public boolean checkUsernameIsTaken(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public boolean checkEmailIsTaken(String email) {

        return userRepository.existsUserByEmail(email);
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
       return userRepository.findUserByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(String username, Long id) {
        currentUser.setUsername(username);
        currentUser.setId(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
