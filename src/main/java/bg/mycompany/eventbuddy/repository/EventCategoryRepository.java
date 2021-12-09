package bg.mycompany.eventbuddy.repository;

import bg.mycompany.eventbuddy.model.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
}
