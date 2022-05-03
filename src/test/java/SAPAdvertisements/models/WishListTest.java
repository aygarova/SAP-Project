package SAPAdvertisements.models;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class WishListTest {
    private WishList wishList;

    @Before
    public void setup(){
        this.wishList = new WishList();
    }

    @Test
    public void testConstructorWithAllParameters(){
        Users mockedUser = Mockito.mock(Users.class);
        Announcements mockedAnnouncements = Mockito.mock(Announcements.class);
        WishList wishList = new WishList(1,mockedUser,mockedAnnouncements);
    }

    @Test
    public void testGetAndSetMethodId(){
        wishList.setWishlist_id(1);
        Assert.assertEquals(1,wishList.getWishlist_id());
    }

    @Test
    public void testGetAndMethodsUser(){
        Users mockedUser = Mockito.mock(Users.class);
        wishList.setUserID(mockedUser);
        Assert.assertEquals(mockedUser,wishList.getUserID());
    }

    @Test
    public void testGetAndMethodsAnnouncements(){
        Announcements mockedAnnouncement = Mockito.mock(Announcements.class);
        wishList.setAnnouncementID(mockedAnnouncement);
        Assert.assertEquals(mockedAnnouncement,wishList.getAnnouncementID());
    }
}