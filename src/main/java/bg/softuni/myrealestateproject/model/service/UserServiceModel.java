package bg.softuni.myrealestateproject.model.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserServiceModel implements UserDetails{
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String phoneNumber;
    private final Collection<GrantedAuthority> authorities;


    public UserServiceModel(Long id, String username, String firstName, String lastName, String password, String phoneNumber, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

//    public String getFullName() {
//        StringBuilder fullName = new StringBuilder();
//        if (getFirstName() != null) {
//            fullName.append(getFirstName());
//        }
//        if (getLastName() != null) {
//            if (!fullName.isEmpty()) {
//                fullName.append(" ");
//            }
//            fullName.append(getLastName());
//        }
//
//        return fullName.toString();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
