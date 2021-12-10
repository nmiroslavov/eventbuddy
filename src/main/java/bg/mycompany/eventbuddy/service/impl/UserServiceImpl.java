package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Picture;
import bg.mycompany.eventbuddy.model.entity.Role;
import bg.mycompany.eventbuddy.model.entity.RoleEnum;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.UserRegistrationServiceModel;
import bg.mycompany.eventbuddy.repository.UserRepository;
import bg.mycompany.eventbuddy.service.CloudinaryService;
import bg.mycompany.eventbuddy.service.RoleService;
import bg.mycompany.eventbuddy.security.SecurityUserServiceImpl;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, SecurityUserServiceImpl securityUserService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.securityUserService = securityUserService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
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
}
