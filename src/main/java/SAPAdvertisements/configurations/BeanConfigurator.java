package SAPAdvertisements.configurations;

import SAPAdvertisements.repository.AnnouncementsRepository;
import SAPAdvertisements.repository.CategoryRepository;
import SAPAdvertisements.repository.UsersRepository;
import SAPAdvertisements.repository.WishListRepository;
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
    public UserService userService(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        return new UserService(usersRepository, passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AnnouncementService announcementService(AnnouncementsRepository announcementsRepository, CategoryRepository categoryRepository, UsersRepository usersRepository){
        return new AnnouncementService(announcementsRepository,categoryRepository,usersRepository);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository){
        return new CategoryService(categoryRepository);
    }

    @Bean
    public WishListService wishListService(WishListRepository wishListRepository, UsersRepository usersRepository, AnnouncementsRepository announcementsRepository){
        return new WishListService(wishListRepository, usersRepository, announcementsRepository);
    }

    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}

}
