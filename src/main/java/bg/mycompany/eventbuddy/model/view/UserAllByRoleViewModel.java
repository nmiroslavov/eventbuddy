package bg.mycompany.eventbuddy.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserAllByRoleViewModel {
    private List<UserByRoleViewModel> users;

    public UserAllByRoleViewModel() {
        this.users = new ArrayList<>();
    }

    public List<UserByRoleViewModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserByRoleViewModel> users) {
        this.users = users;
    }
}
