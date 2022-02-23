package SAPAdvertisements.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "announcements")
public class  Announcements {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int announcement_id;

    @Column(name = "announcementname")
    private String announcementName;

    @Column(name = "price")
    private double price;

    @Column(name = "announcementnumber")
    private String announcementNumber;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "category_id")
    private int category_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "activatefrom")
    @CreationTimestamp
    private LocalDate dateFrom = LocalDate.now();

    @Column(name = "activateto")
    private LocalDate  dateTo = LocalDate.now().plusDays(30);

    @Column(name = "status")
    private String status;



    public Announcements() {
    }

    public Announcements(int announcement_id,  String announcementName, double price,String announcementNumber, String descriptions, String status,LocalDate dateFrom, LocalDate dateTo, int category_id, int user_id) {
        this.announcement_id = announcement_id;
        this.announcementName = announcementName;
        this.price = price;
        this.announcementNumber = announcementNumber;
        this.descriptions = descriptions;
        this.category_id = category_id;
        this.dateFrom =  dateFrom;
        this.dateTo =  dateTo;
        this.user_id = user_id;
        this.status = status;
    }

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
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

    public int getCategoryID() {
        return category_id;
    }

    public void setCategoryID(int category_id) {
        this.category_id = category_id;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Announcement ID = " + announcement_id +
                "Announcement name = " + announcementName +
                ", price = " + price +
                ", announcementNumber = " + announcementNumber +
                ", descriptions = " + descriptions +
                ", category_id = " + category_id +
                ", user_id = " + user_id +
                ", dateFrom = " + dateFrom +
                ", dateTo = " + dateTo +
                ", status = " + status ;
    }

}