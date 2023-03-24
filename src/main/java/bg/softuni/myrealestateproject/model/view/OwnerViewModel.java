package bg.softuni.myrealestateproject.model.view;

public class OwnerViewModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (getFirstName() != null) {
            fullName.append(getFirstName());
        }
        if (getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public OwnerViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OwnerViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OwnerViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
