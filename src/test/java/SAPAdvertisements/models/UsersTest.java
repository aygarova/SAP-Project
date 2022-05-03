package SAPAdvertisements.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UsersTest {
    private Users user;

    @Before
    public void setup(){
    this.user = new Users(1,"Ivaan","Ivan123","1234567890","ivan-ivanov@gmail.com","USER");
    }

    @Test
    public void testEmptyConstructor(){
        Users users = new Users();
    }
    @Test
    public void testGetAndMethodsUsername(){
        user.setUsername("Tonnny");
        Assert.assertEquals("Tonnny",user.getUsername());
    }

    @Test
    public void testGetAndMethodsUserID(){
        Users userWithUserIDInConstructor = new Users(1);
        userWithUserIDInConstructor.setUser_id(1);
        Assert.assertEquals(1,userWithUserIDInConstructor.getUser_id());
    }

    @Test
    public void testGetAndMethodsPassword(){
        user.setPassword("Pass123");
        Assert.assertEquals("Pass123",user.getPassword());
    }

    @Test
    public void tesGetAndMethodstPhoneNumber(){
        user.setPhoneNumber("1234567890");
        Assert.assertEquals("1234567890",user.getPhoneNumber());
    }

    @Test
    public void testGetAndMethodsEmail(){
        user.setEmail("ivan-ivanov@gmail.com");
        Assert.assertEquals("ivan-ivanov@gmail.com",user.getEmail());
    }

    @Test
    public void testGetAndMethodsUserType(){
        user.setUserType("USER");
        Assert.assertEquals("USER",user.getUserType());
    }

}