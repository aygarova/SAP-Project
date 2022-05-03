package SAPAdvertisements.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class WishListDto {
    @NotBlank(message = "Username can't be empty")
    @Size(min = 4, max = 29, message = "Username must be between 4 and 29 elements")
    @Pattern(regexp = "^[A-Za-z]\\w{4,29}$", message = "Username must contains only letter")
    private String username;

    @NotBlank(message = "Number of announcement can't be empty")
    @Size(min = 3, max = 20, message = "Announcement number must be between 3 and 30 elements")
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "Announcement number must be contains letter and/or number")
    private String announcementNumber;

    public WishListDto() {
    }

    public WishListDto(String username, String announcementNumber) {
        this.username = username;
        this.announcementNumber = announcementNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getAnnouncementNumber() {
        return announcementNumber;
    }

    public void setAnnouncementNumber(String announcementNumber) {
        this.announcementNumber = announcementNumber;
    }
}
