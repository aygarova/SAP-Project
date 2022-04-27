package SAPAdvertisements.dtos;

import SAPAdvertisements.enums.AnnouncementStatus;
import SAPAdvertisements.validator.EnumValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AnnouncementDto {

    @NotBlank(message = "Name of announcement can't be empty")
    @Size(max = 30, message = "Announcement name must be maximum 30 elements")
    private String announcementName;

    @Positive(message = "Invalid price of announcement! \n Price must be positive (number over 0)")
    private double price;

    @NotBlank(message = "Number of announcement can't be empty")
    @Size(min = 3, max = 20, message = "Announcement number must be between 3 and 30 elements")
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "Announcement number must be contains letter and/or number")
    private String announcementNumber;

    @NotBlank(message = "Number of announcement can't be empty")
    @Pattern(regexp = "^(.|\\s)*[a-zA-Z]+(.|\\s)*$")
    private String descriptions;

    private String categoryName;

    private String  username;

    private String dateFrom;

    private String  dateTo;

    @NotBlank(message = "Status of announcement can't be empty")
    @EnumValidator(enumClass = AnnouncementStatus.class, message = "Status must be Active or Inactive")
    private String status;

    public AnnouncementDto() {
    }

    public AnnouncementDto(String announcementName, double price, String announcementNumber, String descriptions, String categoryName, String username, String dateFrom, String dateTo, String status) {
        this.announcementName = announcementName;
        this.price = price;
        this.announcementNumber = announcementNumber;
        this.descriptions = descriptions;
        this.categoryName = categoryName;
        this.username =username;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
    }

    public String getAnnouncementName() {
        return announcementName;
    }

    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAnnouncementNumber() {
        return announcementNumber;
    }

    public void setAnnouncementNumber(String announcementNumber) {
        this.announcementNumber = announcementNumber;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
