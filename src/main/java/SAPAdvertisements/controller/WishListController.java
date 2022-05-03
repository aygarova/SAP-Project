package SAPAdvertisements.controller;

import SAPAdvertisements.dtos.WishListDto;
import SAPAdvertisements.exeptions.AnnouncementNotFoundException;
import SAPAdvertisements.exeptions.EmptyWishList;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.WishList;
import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.service.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AnnouncementService announcementService;

    @Autowired

    public WishListController(WishListService wishListService, ModelMapper modelMapper, UserService userService, AnnouncementService announcementService) {
        this.wishListService = wishListService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/{userID}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<WishListDto>> getAnnouncementsFromWishList(@PathVariable("userID") int userID) throws UserNotFoundException, EmptyWishList, AnnouncementNotFoundException {
        List<WishList> wishList= wishListService.readWishList(userID);
        List<WishListDto> wishListDtos = new ArrayList<>();
        for (WishList w: wishList) {
            wishListDtos.add(convertToWishListDto(w));
        }
            return status(OK).body(wishListDtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WishList> addToWishList(@Valid @RequestBody WishListDto wishListDto) throws InvalidPropertyException {
            return status(CREATED).body(wishListService.addToWishList(convertToWishListEntity(wishListDto)));
    }

    @DeleteMapping(value = "{wishlist_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteFromWishList(@PathVariable("wishlist_id") int wishListID) {
        wishListService.deleteAnnouncementFromWishList(wishListID);
        return status(NO_CONTENT).build();
    }

    private WishListDto convertToWishListDto(WishList wishList)  {
        WishListDto wishListDto = new WishListDto();
        wishListDto.setUsername(wishList.getUserID().getUsername());
        wishListDto.setAnnouncementNumber(wishList.getAnnouncementID().getAnnouncementNumber());
        return wishListDto;
    }

    private WishList convertToWishListEntity(WishListDto wishListDto) {
        WishList wishList = new WishList();
        wishList.setUserID(userService.findUserById(wishListDto.getUsername()));
        wishList.setAnnouncementID(announcementService.findAnnouncementsIdNumber(wishListDto.getAnnouncementNumber()));
        return wishList;
    }
}
