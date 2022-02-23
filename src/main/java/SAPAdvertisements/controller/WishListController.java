package SAPAdvertisements.controller;

import SAPAdvertisements.exeptions.EmptyWishList;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.WishList;
import SAPAdvertisements.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping(value = "/{userID}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<WishList>> getAnnouncementsFromWishList(@PathVariable("userID") int userID) {
        try {
            return status(OK).body(wishListService.readWishList(userID));
        } catch (EmptyWishList | UserNotFoundException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WishList> addToWishList(@RequestBody WishList wishList) {
        try {
            return status(CREATED).body(wishListService.addToWishList(wishList));
        } catch (InvalidPropertyException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{wishlist_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteFromWishList(@PathVariable("wishlist_id") int wishListID) {
        wishListService.deleteAnnouncementFromWishList(wishListID);
        return status(NO_CONTENT).build();
    }
}
