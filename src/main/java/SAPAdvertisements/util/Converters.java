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
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final WishListService wishListService;

    @Autowired
    public Converters(AnnouncementService announcementService, CategoryService categoryService, ModelMapper modelMapper, UserService userService, WishListService wishListService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.wishListService = wishListService;
    }


    protected AnnouncementDto convertToAnnouncementsDto(Announcements announcements) throws UserNotFoundException {
        AnnouncementDto announcementDto =  modelMapper.map(announcements,AnnouncementDto.class);
        announcementDto.setUsername(userService.readUser(announcements.getUser_id().getUsername()).getUsername());
        return announcementDto;
    }

    protected Announcements convertToAnnouncementsEntity(AnnouncementDto announcementDto) {
        Announcements announcements = modelMapper.map(announcementDto,Announcements.class);
        announcements.setCategory_id(categoryService.findCategoryId(announcementDto.getCategoryName()));
        announcements.setUser_id(userService.findUserById(announcementDto.getUsername()));
        return announcements;
    }

    protected List<AnnouncementDto> convertToAnnouncementsListDto(List<Announcements> announcements) throws UserNotFoundException {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcements a: announcements) {
            announcementDtos.add(convertToAnnouncementsDto(a));
        }
        return announcementDtos;
    }

    protected Categories convertToCategoryEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Categories.class);
    }

    protected UserDto convertToUserDto(Users users) {
        return modelMapper.map(users,UserDto.class);
    }

    protected Users convertToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto,Users.class);
    }

    protected WishListDto convertToWishListDto(WishList wishList)  {
        WishListDto wishListDto = new WishListDto();
        wishListDto.setUsername(wishList.getUserID().getUsername());
        wishListDto.setAnnouncementNumber(wishList.getAnnouncementID().getAnnouncementNumber());
        return wishListDto;
    }

    protected WishList convertToWishListEntity(WishListDto wishListDto) {
        WishList wishList = new WishList();
        wishList.setUserID(userService.findUserById(wishListDto.getUsername()));
        wishList.setAnnouncementID(announcementService.findAnnouncementsIdNumber(wishListDto.getAnnouncementNumber()));
        return wishList;
    }
}