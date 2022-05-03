package SAPAdvertisements.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "usertype", nullable = false)
    private String userType;

    @OneToMany(mappedBy = "user_id", fetch = FetchType.EAGER)
    private Set<Announcements> announcements;

    @OneToOne(mappedBy = "userID")
    private WishList wishList;

    public Users() {}

    public Users(int user_id,String username, String password, String phoneNumber, String email, String userType,Boolean locked,
                 Boolean enabled) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.locked = locked;
        this.enabled = enabled;
    }

    public Users(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userType);
        return Collections.singleton(authority);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

//    public Set<Announcements> getAnnouncements() {
//        return announcements;
//    }
//
//    public void setAnnouncements(Set<Announcements> announcements) {
//        this.announcements = announcements;
//    }

//    @Override
//    public String toString() {
//        return "Username = " + username +
//                ", password = " + password +
//                ", phoneNumber = " + phoneNumber +
//                ", email = " + email +
//                ", userType = " + userType;
//    }

}