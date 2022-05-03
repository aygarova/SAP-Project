package SAPAdvertisements.dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserDtoTest {
    private UserDto userDto;
//    private static Validator validator;
//
//    @BeforeClass
//    public static void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }

    @Before
    public void setup(){
        this.userDto = new UserDto();
    }

    @Test
    public void testConstructorWithParameter(){
        UserDto userDto = new UserDto("Tonnny", "Pass123", "0321456987", "gjgj.hg@gmail.com", "USER");
    }

    @Test
    public void testGetAndMethodsUsername(){
        userDto.setUsername("Tonnny");
        Assert.assertEquals("Tonnny",userDto.getUsername());
    }


    @Test
    public void testGetAndMethodsPassword(){
        userDto.setPassword("Pass123");
        Assert.assertEquals("Pass123",userDto.getPassword());
    }

    @Test
    public void tesGetAndMethodstPhoneNumber(){
        userDto.setPhoneNumber("1234567890");
        Assert.assertEquals("1234567890",userDto.getPhoneNumber());
    }

    @Test
    public void testGetAndMethodsEmail(){
        userDto.setEmail("ivan-ivanov@gmail.com");
        Assert.assertEquals("ivan-ivanov@gmail.com",userDto.getEmail());
    }

    @Test
    public void testGetAndMethodsUserType(){
        userDto.setUserType("USER");
        Assert.assertEquals("USER",userDto.getUserType());
    }
}