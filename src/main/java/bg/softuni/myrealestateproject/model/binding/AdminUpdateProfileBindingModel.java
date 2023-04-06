package bg.softuni.myrealestateproject.model.binding;

import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdminUpdateProfileBindingModel {
    private Long id;
    @NotBlank(message = "First name is required!")
    private String firstName;

    private String lastName;

    @Email(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    @NotNull(message = "Role is required!")
    private RoleTypeEnum role;

    public AdminUpdateProfileBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public AdminUpdateProfileBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AdminUpdateProfileBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AdminUpdateProfileBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminUpdateProfileBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AdminUpdateProfileBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RoleTypeEnum getRole() {
        return role;
    }

    public AdminUpdateProfileBindingModel setRole(RoleTypeEnum role) {
        this.role = role;
        return this;
    }
}
