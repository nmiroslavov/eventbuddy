package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.binding.UserUpdateBindingModel;
import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserRegistrationServiceModel;
import bg.mycompany.eventbuddy.model.service.UserUpdateServiceModel;
import bg.mycompany.eventbuddy.model.view.UserCurrentDetailsViewModel;
import bg.mycompany.eventbuddy.repository.UserRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.PictureService;
import bg.mycompany.eventbuddy.service.RoleService;
import bg.mycompany.eventbuddy.security.SecurityUserServiceImpl;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final SecurityUserServiceImpl securityUserService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final PictureService pictureService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, SecurityUserServiceImpl securityUserService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, PictureService pictureService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
    }

    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {

        User user = modelMapper.map(userRegistrationServiceModel, User.class);

        // encode password
        user.setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()));

        // find and set role for the newly registered user
        Role userRole = roleService.findByRole(RoleEnum.USER);
        user.setRoles(Set.of(userRole));
        user.setProfileCreationDateTime(LocalDateTime.now());
        Picture defaultProfilePicture = cloudinaryService.getDefaultPicture();
        user.setProfilePicture(defaultProfilePicture);

        userRepository.save(user);

        UserDetails principal = securityUserService.loadUserByUsername(user.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, user.getPassword(), principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public Optional<User> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserCurrentDetailsViewModel getLoggedInUserViewModel(String userIdentifier) {

        User currentUser = userRepository.findByUsername(userIdentifier).orElseThrow(() -> new UsernameNotFoundException(userIdentifier));

        UserCurrentDetailsViewModel userCurrentDetailsViewModel = modelMapper.map(currentUser, UserCurrentDetailsViewModel.class);
        userCurrentDetailsViewModel.setProfilePictureUrl(currentUser.getProfilePicture().getUrl());
        userCurrentDetailsViewModel.setCreatedOn(currentUser.getProfileCreationDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        return userCurrentDetailsViewModel;
    }

    @Override
    public UserUpdateBindingModel getUserUpdateBindingModel(String userIdentifier) {

        User currentUser = userRepository.findByUsername(userIdentifier).orElseThrow(() -> new UsernameNotFoundException(userIdentifier));
        UserUpdateBindingModel userUpdateBindingModel = modelMapper.map(currentUser, UserUpdateBindingModel.class);

        return userUpdateBindingModel;
    }

    @Override
    public void updateUser(UserUpdateServiceModel userUpdateServiceModel, String userIdentifier) throws IOException {

        User userToBeUpdated = userRepository.findByUsername(userIdentifier).orElseThrow(() -> new UsernameNotFoundException(userIdentifier));

        userToBeUpdated.setFirstName(userUpdateServiceModel.getFirstName());
        userToBeUpdated.setLastName(userUpdateServiceModel.getLastName());
        userToBeUpdated.setAge(userUpdateServiceModel.getAge());
        if (!userUpdateServiceModel.getPicture().isEmpty()) {
            if (pictureService.isProfilePictureDefault(userToBeUpdated.getProfilePicture())) {
                Picture picture = pictureService.updateDefaultProfilePicture(userUpdateServiceModel.getPicture());
                userToBeUpdated.setProfilePicture(picture);
            } else {
                pictureService.updateProfilePicture(userUpdateServiceModel.getPicture(), userToBeUpdated.getProfilePicture());
            }
        }

        userRepository.save(userToBeUpdated);
    }
}
