package SAPAdvertisements.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "announcements")
public class  Announcements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int announcement_id;

    @Column(name = "announcement_name", nullable = false)
    private String announcementName;

    @Column(name = "price",nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double price;

    @Column(name = "announcement_number", nullable = false, unique = true)
    private String announcementNumber;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "activate_from", nullable = false, columnDefinition = "DATE")
    private LocalDate dateFrom = LocalDate.now();

    @Column(name = "activate_to", nullable = false, columnDefinition = "DATE")
    private LocalDate  dateTo = LocalDate.now().plusDays(30);

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user_id;

    @OneToMany(mappedBy = "announcementID", fetch = FetchType.EAGER)
    private Set<WishList> wishLists;

    public Announcements() {
    }

    public Announcements(int announcement_id, String announcementName, double price, String announcementNumber, String descriptions, LocalDate dateFrom, LocalDate dateTo, String status) {
        this.announcement_id = announcement_id;
        this.announcementName = announcementName;
        this.price = price;
        this.announcementNumber = announcementNumber;
        this.descriptions = descriptions;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
    }

    public Announcements(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    public Announcements(String announcementNumber) {
        this.announcementNumber = announcementNumber;
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

    public Categories getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Categories category_id) {
        this.category_id = category_id;
    }

    public Users getUser_id() {
        return user_id;
    }

    public void setUser_id(Users user_id) {
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


//    @Override
//    public String toString() {
//        return "Announcement ID = " + announcement_id +
//                "Announcement name = " + announcementName +
//                ", price = " + price +
//                ", announcementNumber = " + announcementNumber +
//                ", descriptions = " + descriptions +
//                ", category_id = " + category_id +
//                ", user_id = " + user_id +
//                ", dateFrom = " + dateFrom +
//                ", dateTo = " + dateTo +
//                ", status = " + status ;
//    }

}