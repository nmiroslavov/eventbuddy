package bg.mycompany.eventbuddy.model.binding;

import bg.mycompany.eventbuddy.model.entity.EventCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventUpdateBindingModel {

    @Size(min = 6, max = 20)
    private String name;


    @Size(min = 10, max = 400)
    private String description;


    private EventCategoryEnum category;


    private MultipartFile coverPicture;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    private LocalDateTime startDateTime;

    @PositiveOrZero
    private BigDecimal ticketPrice;

    public EventUpdateBindingModel() {
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
