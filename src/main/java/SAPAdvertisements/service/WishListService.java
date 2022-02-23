package SAPAdvertisements.service;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.EmptyWishList;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.models.WishList;
import SAPAdvertisements.repository.AnnouncementsRepository;
import SAPAdvertisements.repository.UsersRepository;
import SAPAdvertisements.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AnnouncementsRepository announcementsRepository;


    public List<WishList> readWishList(int userID) throws EmptyWishList, UserNotFoundException {
        List<WishList> allAnnouncementsFromWishListFromDB = wishListRepository.findByUserID(userID);
        if ((usersRepository.findById(userID)).isEmpty()){
            throw new UserNotFoundException(ConstantMessages.USER_NOT_FOUND_EXCEPTION);
        }
        if (allAnnouncementsFromWishListFromDB.size() == 0){
            throw new EmptyWishList(ConstantMessages.WISHLIST_IS_EMPTY);
        }
        return allAnnouncementsFromWishListFromDB;
    }

    public WishList addToWishList(WishList wishList) throws InvalidPropertyException {
        if (!haveThisUserById(wishList.getUserID())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_USER_ID_EXCEPTION);
        }

        if (!haveThisAnnouncementById(wishList.getAnnouncementID())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_ANNOUNCEMENT_ID_EXCEPTION);
        }

        return wishListRepository.save(wishList);
    }

    public void deleteAnnouncementFromWishList(int wishListID) {
        wishListRepository.deleteById(wishListID);
    }

    private boolean haveThisAnnouncementById(int announcementID) {
        List<Announcements> announcementsFromDB = announcementsRepository.findAll();
        for (Announcements a:announcementsFromDB) {
            if (a.getAnnouncement_id() == announcementID){
                return true;
            }
        }
        return false;
    }

    private boolean haveThisUserById(int userID) {
        List<Users> userFromDB = usersRepository.findAll();
        for (Users u:userFromDB) {
            if (u.getUser_id() == userID){
                return true;
            }
        }
        return false;
    }


}
