package bg.mycompany.eventbuddy.model.view;

public class EventDetailsViewModel {
    private String name;
    private String description;
    private String coverPictureUrl;
    private String creatorUsername;
    private Integer attendeesCount;
    private String category;
    private String startDate;
    private String startTime;
    private Integer ticketPrice;
    private boolean canDelete;
    private boolean canSignUp;
    private boolean canSignOut;
    private boolean canUpdate;

    public EventDetailsViewModel() {
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

    public String getCoverPictureUrl() {
        return coverPictureUrl;
    }

    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public Integer getAttendeesCount() {
        return attendeesCount;
    }

    public void setAttendeesCount(Integer attendeesCount) {
        this.attendeesCount = attendeesCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanSignUp() {
        return canSignUp;
    }

    public void setCanSignUp(boolean canSignUp) {
        this.canSignUp = canSignUp;
    }

    public boolean isCanSignOut() {
        return canSignOut;
    }

    public void setCanSignOut(boolean canSignOut) {
        this.canSignOut = canSignOut;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}
