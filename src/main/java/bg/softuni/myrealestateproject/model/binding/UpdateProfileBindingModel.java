package bg.softuni.myrealestateproject.model.binding;

import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateProfileBindingModel {

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

    public UpdateProfileBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public UpdateProfileBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UpdateProfileBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateProfileBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UpdateProfileBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UpdateProfileBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RoleTypeEnum getRole() {
        return role;
    }

    public UpdateProfileBindingModel setRole(RoleTypeEnum role) {
        this.role = role;
        return this;
    }
}
