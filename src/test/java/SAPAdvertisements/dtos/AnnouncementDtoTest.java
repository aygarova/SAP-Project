package SAPAdvertisements.dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnouncementDtoTest {
    private AnnouncementDto announcementDto;

    @Before
    public void setup(){
       this.announcementDto = new AnnouncementDto("Dress", 75.00, "YP798w", "Red dress","Clothes","Tonnny","2022-04-23","2022-05-23",  "ACTIVE");
    }

    @Test
    public void testEmptyConstructor(){
        AnnouncementDto announcements = new AnnouncementDto();
    }


    @Test
    public void testGetAndMethodsAnnouncementName(){
        announcementDto.setAnnouncementName("Dress");
        Assert.assertEquals("Dress",announcementDto.getAnnouncementName());
    }

    @Test
    public void testGetAndMethodsPrice(){
        announcementDto.setPrice(75.00);
        Assert.assertEquals(75.00,announcementDto.getPrice(),0.0);
    }

    @Test
    public void testGetAndMethodsAnnouncementNumber(){
        announcementDto.setAnnouncementNumber("YP798w");
        Assert.assertEquals("YP798w",announcementDto.getAnnouncementNumber());
    }

    @Test
    public void testSetGetAndMethodsDescriptions(){
        announcementDto.setDescriptions("Red dress");
        Assert.assertEquals("Red dress",announcementDto.getDescriptions());
    }

    @Test
    public void testGetAndMethodsStatus(){
        announcementDto.setStatus("ACTIVE");
        Assert.assertEquals("ACTIVE",announcementDto.getStatus());
    }

    @Test
    public void testGetAndMethodsDateFrom(){
        announcementDto.setDateFrom("2022-04-23");
        Assert.assertEquals("2022-04-23",announcementDto.getDateFrom());
    }

    @Test
    public void testGetAndMethodsDateTo(){
        announcementDto.setDateTo("2022-05-23");
        Assert.assertEquals("2022-05-23",announcementDto.getDateTo());
    }

    @Test
    public void testGetAndMethodsCategory(){
        announcementDto.setCategoryName("Toys");
        Assert.assertEquals("Toys",announcementDto.getCategoryName());
    }

    @Test
    public void testGetAndMethodsUser(){
        announcementDto.setUsername("Tonnny");
        Assert.assertEquals("Tonnny",announcementDto.getUsername());
    }
}