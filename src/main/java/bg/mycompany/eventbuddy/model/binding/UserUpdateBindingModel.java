package bg.mycompany.eventbuddy.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class UserUpdateBindingModel {

    @Size(min = 4, max = 16)
    private String firstName;

    @Size(min = 4, max = 16)
    private String lastName;

    @Min(18)
    @Max(100)
    private Integer age;
    private MultipartFile picture;

    public UserUpdateBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
