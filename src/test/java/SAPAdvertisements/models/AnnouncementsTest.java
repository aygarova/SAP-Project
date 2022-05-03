package SAPAdvertisements.models;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;


public class AnnouncementsTest {
    private Announcements announcementsWithFullConstructor;
    private LocalDate from;
    private LocalDate to;

    @Before
    public void setup(){
         this.from = LocalDate.now();
         this.to = LocalDate.now().plusDays(30);
        this.announcementsWithFullConstructor = new Announcements(1,"Dress", 75.00, "YP798w", "Red dress", from, to, "ACTIVE");
    }

    @Test
    public void testEmptyConstructor(){
        Announcements announcementsWithEmptyConstructor = new Announcements();
    }

    @Test
    public void testConstructorsWithIdParameter(){
        Announcements announcements = new Announcements(1);
    }

    @Test
    public void testConstructorsAnnouncementNumberParameter(){
        Announcements announcements = new Announcements("YP797w");
    }

    @Test
    public void testGetAndMethodsAnnouncementId(){
        announcementsWithFullConstructor.setAnnouncement_id(1);
        Assert.assertEquals(1,announcementsWithFullConstructor.getAnnouncement_id());
    }

    @Test
    public void testGetAndMethodsAnnouncementName(){
        announcementsWithFullConstructor.setAnnouncementName("Dress");
        Assert.assertEquals("Dress",announcementsWithFullConstructor.getAnnouncementName());
    }

    @Test
    public void testGetAndMethodsPrice(){
        announcementsWithFullConstructor.setPrice(75.00);
        Assert.assertEquals(75.00,announcementsWithFullConstructor.getPrice(),0.0);
    }

    @Test
    public void testGetAndMethodsAnnouncementNumber(){
        announcementsWithFullConstructor.setAnnouncementNumber("YP798w");
        Assert.assertEquals("YP798w",announcementsWithFullConstructor.getAnnouncementNumber());
    }

    @Test
    public void testSetGetAndMethodsDescriptions(){
        announcementsWithFullConstructor.setDescriptions("Red dress");
        Assert.assertEquals("Red dress",announcementsWithFullConstructor.getDescriptions());
    }

    @Test
    public void testGetAndMethodsStatus(){
        announcementsWithFullConstructor.setStatus("ACTIVE");
        Assert.assertEquals("ACTIVE",announcementsWithFullConstructor.getStatus());
    }

    @Test
    public void testGetAndMethodsDateFrom(){
        announcementsWithFullConstructor.setDateFrom(from);
        Assert.assertEquals(from,announcementsWithFullConstructor.getDateFrom());
    }

    @Test
    public void testGetAndMethodsDateTo(){
        announcementsWithFullConstructor.setDateTo(to);
        Assert.assertEquals(to,announcementsWithFullConstructor.getDateTo());
    }

    @Test
    public void testGetAndMethodsCategory(){
        Categories mockedCategory = Mockito.mock(Categories.class);
        announcementsWithFullConstructor.setCategory_id(mockedCategory);
        Assert.assertEquals(mockedCategory,announcementsWithFullConstructor.getCategory_id());
    }

    @Test
    public void testGetAndMethodsUser(){
        Users mockedUser = Mockito.mock(Users.class);
        announcementsWithFullConstructor.setUser_id(mockedUser);
        Assert.assertEquals(mockedUser,announcementsWithFullConstructor.getUser_id());
    }

}