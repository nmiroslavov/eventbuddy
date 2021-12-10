package bg.mycompany.eventbuddy.model.service;

import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventUpdateServiceModel {
    private String name;
    private String description;
    private EventCategoryEnum category;
    private MultipartFile coverPicture;
    private LocalDateTime startDateTime;
    private BigDecimal ticketPrice;

    public EventUpdateServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(EventCategoryEnum category) {
        this.category = category;
    }

    public MultipartFile getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(MultipartFile coverPicture) {
        this.coverPicture = coverPicture;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
