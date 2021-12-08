package bg.mycompany.eventbuddy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "event_categories")
public class EventCategory extends BaseEntity {

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategoryEnum category;

    public EventCategory() {
    }

    public EventCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(EventCategoryEnum category) {
        this.category = category;
    }
}
