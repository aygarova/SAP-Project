package SAPAdvertisements.dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class WishListDtoTest {
    private WishListDto wishListDto;

    @Before
    public void setup(){
        this.wishListDto = new WishListDto();
    }

    @Test
    public void testConstructorWithAllParameters(){
       WishListDto wishListDto = new WishListDto("Tonnny","YP77391wws");
    }

    @Test
    public void testGetAndMethodsUsername(){
        wishListDto.setUsername("Tonnny");
        Assert.assertEquals("Tonnny",wishListDto.getUsername());
    }

    @Test
    public void testGetAndMethodsAnnouncements(){
        wishListDto.setAnnouncementNumber("YP77391wws");
        Assert.assertEquals("YP77391wws",wishListDto.getAnnouncementNumber());
    }
}