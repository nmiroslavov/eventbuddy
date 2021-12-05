package bg.mycompany.eventbuddy.repository;

import bg.mycompany.eventbuddy.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
