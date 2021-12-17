package bg.mycompany.eventbuddy.model.view;

import bg.mycompany.eventbuddy.model.entity.RoleEnum;

public class UserByRoleViewModel {
    private Long id;
    private String username;
    private String roleEnum;
    private String email;

    public UserByRoleViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(String roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
