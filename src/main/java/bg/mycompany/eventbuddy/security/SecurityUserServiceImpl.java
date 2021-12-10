package bg.mycompany.eventbuddy.security;

import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.repository.UserRepository;
import bg.mycompany.eventbuddy.security.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user has been registered"));

        return mapToUserDetails(user);
    }

    private static UserDetails mapToUserDetails(User user) {
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().toString()))
                .collect(Collectors.toList());

        return new SecurityUser(user.getUsername(), user.getPassword(), authorities);
    }
}
