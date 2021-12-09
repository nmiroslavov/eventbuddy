package bg.mycompany.eventbuddy.model.service;

import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventAddServiceModel {
    private String creatorUsername;
    private String name;
    private String description;
    private MultipartFile coverPicture;
    private EventCategoryEnum category;
    private LocalDateTime startDateTime;
    private BigDecimal ticketPrice;

    public EventAddServiceModel() {
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
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

    public MultipartFile getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(MultipartFile coverPicture) {
        this.coverPicture = coverPicture;
    }

    public EventCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(EventCategoryEnum category) {
        this.category = category;
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
