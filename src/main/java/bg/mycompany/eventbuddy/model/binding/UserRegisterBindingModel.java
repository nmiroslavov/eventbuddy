package bg.mycompany.eventbuddy.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 16)
    private String username;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 16)
    private String matchingPassword;



}
