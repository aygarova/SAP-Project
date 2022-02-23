package SAPAdvertisements.configurations;

import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.service.WishListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurator {
    @Bean
    public UserService userService(){
        return new UserService();
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
}
