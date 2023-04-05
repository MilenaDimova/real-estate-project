package bg.softuni.myrealestateproject.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateProfileBindingModel {

    private Long id;
    @NotBlank(message = "First name is required!")
    private String firstName;

    private String lastName;

    @Email(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

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
}
