package bg.mycompany.eventbuddy.repository;

import bg.mycompany.eventbuddy.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("delete from Event e where e.startDateTime < current_date")
    List<Event> deleteAllByPastDateTime();
}
