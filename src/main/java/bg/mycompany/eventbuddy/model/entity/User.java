package bg.mycompany.eventbuddy.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new LinkedHashSet<>();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToOne()
    private Picture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "attendees")
    private List<Event> hostedAndSignedEvents = new ArrayList<>();

    @Column(name = "profile_creation_date_time", nullable = false)
    private LocalDateTime profileCreationDateTime;

    public User() {
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

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Event> getHostedAndSignedEvents() {
        return hostedAndSignedEvents;
    }

    public void setHostedAndSignedEvents(List<Event> hostedAndSignedEvents) {
        this.hostedAndSignedEvents = hostedAndSignedEvents;
    }

    public LocalDateTime getProfileCreationDateTime() {
        return profileCreationDateTime;
    }

    public void setProfileCreationDateTime(LocalDateTime profileCreationDateTime) {
        this.profileCreationDateTime = profileCreationDateTime;
    }
}
