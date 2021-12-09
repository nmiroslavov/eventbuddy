package bg.mycompany.eventbuddy.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class PictureUploadBindingModel {
    private String title;
    private MultipartFile picture;

    public PictureUploadBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
