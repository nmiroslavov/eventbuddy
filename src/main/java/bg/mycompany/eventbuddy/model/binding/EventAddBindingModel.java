package bg.mycompany.eventbuddy.model.binding;

import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventAddBindingModel {
    @NotNull
    @Size(min = 6, max = 20)
    private String name;

    @NotNull
    @Size(min = 10, max = 200)
    private String description;

    @NotNull
    private MultipartFile coverPicture;

    @NotNull
    private EventCategoryEnum category;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    private LocalDateTime startDateTime;

    @PositiveOrZero
    private BigDecimal ticketPrice;

    public EventAddBindingModel() {
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


    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}
