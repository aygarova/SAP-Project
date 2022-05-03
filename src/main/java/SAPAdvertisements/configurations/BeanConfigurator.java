package SAPAdvertisements.configurations;

import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.service.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfigurator {
    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AnnouncementService announcementService(){
        return new AnnouncementService();
    }

    @Bean
    public CategoryService categoryService(){
        return new CategoryService();
    }

    @Bean
    public WishListService wishListService(){
        return new WishListService();
    }

    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}

}
