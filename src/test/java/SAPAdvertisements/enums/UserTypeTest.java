package SAPAdvertisements.enums;

import org.junit.Assert;
import org.junit.Test;

public class UserTypeTest {
    @Test
    public void testGetType(){
        UserType userType = UserType.valueOf("USER");
        Assert.assertEquals("USER",userType.getType());
    }
}