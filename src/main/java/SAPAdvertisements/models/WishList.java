package SAPAdvertisements.models;

import javax.persistence.*;

@Entity
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlist_id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userID;

    @ManyToOne
    @JoinColumn (name = "announcement_id", nullable = false)
    private Announcements announcementID;

    public WishList() {
    }

    public WishList(int wishlist_id, Users userID, Announcements announcementID) {
        this.wishlist_id = wishlist_id;
        this.userID = userID;
        this.announcementID = announcementID;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Announcements getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(Announcements announcementID) {
        this.announcementID = announcementID;
    }
}
