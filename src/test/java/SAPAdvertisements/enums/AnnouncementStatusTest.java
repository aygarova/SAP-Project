package SAPAdvertisements.enums;

import org.junit.Assert;
import org.junit.Test;

public class AnnouncementStatusTest {
    @Test
    public void testGetStatus(){
        AnnouncementStatus announcementStatus = AnnouncementStatus.valueOf("ACTIVE");
        Assert.assertEquals("ACTIVE",announcementStatus.getStatus());
    }
}