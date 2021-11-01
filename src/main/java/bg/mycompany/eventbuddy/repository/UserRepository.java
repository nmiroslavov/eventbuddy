package bg.mycompany.eventbuddy.repository;

import bg.mycompany.eventbuddy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
