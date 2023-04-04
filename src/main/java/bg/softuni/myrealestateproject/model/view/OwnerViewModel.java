package bg.softuni.myrealestateproject.model.view;

import java.util.List;

public class OwnerViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private int countActiveOffer;
    private List<OfferViewModel> offers;
    private List<String> roles;

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

    public Long getId() {
        return id;
    }

    public OwnerViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public OwnerViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        if (this.lastName.isBlank()) {
           this.lastName = "*Not filled*";
        }
        return this.lastName;
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

    public String getEmail() {
        return email;
    }

    public OwnerViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getCountActiveOffer() {
        return countActiveOffer;
    }

    public OwnerViewModel setCountActiveOffer(int countActiveOffer) {
        this.countActiveOffer = countActiveOffer;
        return this;
    }

    public List<OfferViewModel> getOffers() {
        return offers;
    }

    public OwnerViewModel setOffers(List<OfferViewModel> offers) {
        this.offers = offers;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public OwnerViewModel setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
