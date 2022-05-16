package SAPAdvertisements.util;

import SAPAdvertisements.dtos.AnnouncementDto;
import SAPAdvertisements.dtos.CategoryDto;
import SAPAdvertisements.dtos.UserDto;
import SAPAdvertisements.dtos.WishListDto;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.models.WishList;
import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.service.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Converters  {
    public static ModelMapper modelMapper = new ModelMapper();;

    public static AnnouncementDto convertToAnnouncementsDto(Announcements announcements, UserService userService) throws UserNotFoundException {
        AnnouncementDto announcementDto =  modelMapper.map(announcements,AnnouncementDto.class);
        announcementDto.setUsername(userService.readUser(announcements.getUser_id().getUsername()).getUsername());
        return announcementDto;
    }

    public static Announcements convertToAnnouncementsEntity(AnnouncementDto announcementDto, UserService userService,CategoryService categoryService) {
        Announcements announcements = modelMapper.map(announcementDto,Announcements.class);
        announcements.setCategory_id(categoryService.findCategoryId(announcementDto.getCategoryName()));
        announcements.setUser_id(userService.findUserById(announcementDto.getUsername()));
        return announcements;
    }

    public static List<AnnouncementDto> convertToAnnouncementsListDto(List<Announcements> announcements, UserService userService) throws UserNotFoundException {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcements a: announcements) {
            announcementDtos.add(convertToAnnouncementsDto(a,userService));
        }
        return announcementDtos;
    }
    public static UserDto convertToUserDto(Users users) {
        return modelMapper.map(users,UserDto.class);
    }

    public static Users convertToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto,Users.class);
    }

    public static Categories convertToCategoryEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Categories.class);
    }

    public static WishListDto convertToWishListDto(WishList wishList)  {
        WishListDto wishListDto = new WishListDto();
        wishListDto.setUsername(wishList.getUserID().getUsername());
        wishListDto.setAnnouncementNumber(wishList.getAnnouncementID().getAnnouncementNumber());
        return wishListDto;
    }

    public static WishList convertToWishListEntity(WishListDto wishListDto, UserService userService, AnnouncementService announcementService) {
        WishList wishList = new WishList();
        wishList.setUserID(userService.findUserById(wishListDto.getUsername()));
        wishList.setAnnouncementID(announcementService.findAnnouncementsIdNumber(wishListDto.getAnnouncementNumber()));
        return wishList;
    }
}
