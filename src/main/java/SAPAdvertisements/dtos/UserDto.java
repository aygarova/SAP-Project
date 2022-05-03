package SAPAdvertisements.dtos;

import SAPAdvertisements.enums.UserType;
import SAPAdvertisements.validator.EnumValidator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Username can't be empty")
    @Size(min = 4, max = 29, message = "Username must be between 4 and 29 elements")
    @Pattern(regexp = "^[A-Za-z]\\w{4,29}$", message = "Username must contains only letter")
    private String username;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 elements")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$")
    private String password;

    @NotBlank(message = "Phone number can't be empty")
    @Pattern(regexp="[\\d]{10}", message = "Phone number must contains only 10 digits")
    private String phoneNumber;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "User Type can't be empty")
    @EnumValidator(enumClass = UserType.class, message = "UserType must be USER or ADMIN")
    private String userType;

    public UserDto() {
    }

    public UserDto(String username, String password, String phoneNumber, String email, String userType) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
