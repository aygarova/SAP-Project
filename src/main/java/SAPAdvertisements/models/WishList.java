package SAPAdvertisements.models;

import javax.persistence.*;

@Entity
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int wishlist_id;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "announcement_id")
    private Integer announcementID;

    public WishList() {
    }

    public WishList(int wishlist_id, int announcement_id, int user_id) {
        this.wishlist_id = wishlist_id;
        this.announcementID = announcement_id;
        this.userID = user_id;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public Integer getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(Integer announcementID) {
        this.announcementID = announcementID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
