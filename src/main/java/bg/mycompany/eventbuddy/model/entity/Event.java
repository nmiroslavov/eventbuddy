package bg.mycompany.eventbuddy.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private User creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
