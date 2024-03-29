package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @ManyToOne
    private StatusEntity status;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<OfferEntity> offers;

    public UserEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

//    public void addAdminRole() {
//        RoleEntity adminRole = getRoles().get(0);
//        getRoles().add(adminRole);
//    }

    public List<OfferEntity> getOffers() {
        return offers;
    }

    public UserEntity setOffers(List<OfferEntity> offers) {
        this.offers = offers;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public UserEntity setStatus(StatusEntity status) {
        this.status = status;
        return this;
    }
}
