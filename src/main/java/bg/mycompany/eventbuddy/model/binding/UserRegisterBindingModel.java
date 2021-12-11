package bg.mycompany.eventbuddy.model.binding;

import javax.validation.constraints.*;

public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 4, max = 16)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 16)
    private String password;

    @NotNull
    @Size(min = 6, max = 16)
    private String matchingPassword;

    @NotNull
    @Size(min = 4, max = 16)
    private String firstName;

    @NotNull
    @Size(min = 4, max = 16)
    private String lastName;

    @NotNull
    @Min(18)
    @Max(100)
    private Integer age;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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
}
