package bg.mycompany.eventbuddy.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 4, max = 15)
    private String username;

    @NotNull
    @Size(min = 2, max = 15)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 15)
    private String password;

    @NotNull
    @Size(min = 6, max = 15)
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
