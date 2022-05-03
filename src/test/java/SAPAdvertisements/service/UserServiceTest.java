package SAPAdvertisements.service;

import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.repository.UsersRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
    private Users testUser;
    private UserService userService;
    private UsersRepository mockedUsersRepository;
    private PasswordEncoder mockedPasswordEncoder;


    @Before
    public void setup(){
        this.testUser = new Users(1,"Ivaan","Ivan123","1234567890","ivan-ivanov@gmail.com","USER");;
        this.mockedUsersRepository = Mockito.mock(UsersRepository.class);
        this.mockedPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        this.userService = new UserService(mockedUsersRepository, mockedPasswordEncoder);
    }

    @Test
    public void testReadUser() throws UserNotFoundException {
        Mockito.when(mockedUsersRepository.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        Assert.assertEquals(testUser,userService.readUser("Ivaan"));
    }
}