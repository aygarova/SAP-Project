package SAPAdvertisements.service;

import SAPAdvertisements.validator.Validations;
import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.AnnouncementNotFoundException;
import SAPAdvertisements.exeptions.EmptyWishList;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.NonUpdateablePropertyException;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.models.Categories;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.repository.AnnouncementsRepository;
import SAPAdvertisements.repository.CategoryRepository;
import SAPAdvertisements.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnnouncementService {

    @Autowired
    private AnnouncementsRepository announcementsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Announcements readAnnouncement(String announcementNumber) throws AnnouncementNotFoundException {
        Announcements announcementToReturn = null;
        List<Announcements> announcementsFromDB = announcementsRepository.findAll();
        for (Announcements a : announcementsFromDB) {
            if (a.getAnnouncementNumber().equals(announcementNumber)){
                announcementToReturn = a;
            }
        }
        if (announcementToReturn == null){
            throw new AnnouncementNotFoundException(ConstantMessages.ANNOUNCEMENT_NOT_FOUND_EXCEPTION);
        }
        return announcementToReturn;
    }

    public Announcements createAnnouncement(Announcements announcement) throws InvalidPropertyException {
        if (!Validations.isValidAnnouncementName(announcement.getAnnouncementName())){
           throw new InvalidPropertyException(ConstantMessages.INVALID_NAME_ANNOUNCEMENT_EXCEPTION);
        }

        if (!Validations.isValidPrice(announcement.getPrice())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_PRICE_ANNOUNCEMENT_EXCEPTION);
        }

        if (!Validations.isValidAnnouncementNumber(announcement.getAnnouncementNumber())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_ITEM_NUMBER_ANNOUNCEMENT_EXCEPTION);
        }

        if (isSameAnnouncementNumber(announcement.getAnnouncementNumber())){
            throw new InvalidPropertyException(ConstantMessages.SAME_ANNOUNCEMENT_NUMBER);
        }

        if (!Validations.isValidDescriptions(announcement.getDescriptions())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_DESCRIPTION_ANNOUNCEMENT_EXCEPTION);
        }

        if (!haveThisCategory(announcement.getCategoryID())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
        }

        if (!Validations.isValidAnnouncementStatus(announcement.getStatus())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_STATUS_EXCEPTION);
        }

        if (!haveThisUserById(announcement.getUserID())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_USERNAME_EXCEPTION);
        }

        return announcementsRepository.save(announcement);
    }


    public Announcements updateAnnouncement(String announcementNumber, String field, String data) throws AnnouncementNotFoundException, NonUpdateablePropertyException, InvalidPropertyException {
        Announcements announcement = readAnnouncement(announcementNumber);
        switch (field){
            case "announcementName":
                if (!Validations.isValidAnnouncementName(data)){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_NAME_ANNOUNCEMENT_EXCEPTION);
                }
                announcement.setAnnouncementName(data);
                break;
            case "announcementNumber":
                throw new NonUpdateablePropertyException(ConstantMessages.NON_UPDATEABLE_ANNOUNCEMENT_NUMBER_EXCEPTIONS);
            case "price":
                try {
                    if (!Validations.isValidPrice(Double.parseDouble(data))){
                        throw new InvalidPropertyException(ConstantMessages.INVALID_PRICE_ANNOUNCEMENT_EXCEPTION);
                    }
                }catch (NumberFormatException e){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_PRICE_ANNOUNCEMENT_EXCEPTION);
                }

                announcement.setPrice(Double.parseDouble(data));
                break;
            case "descriptions":
                if (!Validations.isValidDescriptions(data)){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_DESCRIPTION_ANNOUNCEMENT_EXCEPTION);
                }
                announcement.setDescriptions(data);
                break;
            case "status":
                if (!Validations.isValidAnnouncementStatus(data)){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_STATUS_EXCEPTION);
                }
                announcement.setStatus(data);
                break;
            case "categoryID":
                try {
                    if (!haveThisCategory(Integer.parseInt(data))){
                        throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
                    }
                }catch (NumberFormatException e){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
                }

                announcement.setCategoryID(Integer.parseInt(data));
                break;
            case "userID":
                try {
                    if (!haveThisUserById(Integer.parseInt(data))){
                        throw new InvalidPropertyException(ConstantMessages.INVALID_USERNAME_EXCEPTION);
                    }
                }catch (NumberFormatException e){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
                }

                announcement.setUserID(Integer.parseInt(data));
                break;
        }
        announcementsRepository.save(announcement);
        return announcement;
    }

    public void deleteAnnouncement(Announcements announcements) {
        announcementsRepository.deleteById(announcements.getAnnouncement_id());
    }

    public Announcements inactiveAnnouncement(String announcementNumber) throws AnnouncementNotFoundException, InvalidPropertyException, NonUpdateablePropertyException {
        return updateAnnouncement(announcementNumber,"status","Inactive");
    }

    public boolean isSameAnnouncementNumber(String announcementNumber){
        List<Announcements> announcements = announcementsRepository.findAll();
        for (Announcements a:announcements) {
            if (a.getAnnouncementNumber().equals(announcementNumber)){
                return true;
            }
        }
        return false;
    }

    public List<Announcements> getByStatusAndTimePeriod(String status, String startTime, String endTime) throws ParseException, EmptyWishList {
        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse(startTime);
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse(endTime);
        List<String> listStatus = List.of(status.split(","));
        List<Announcements> announcements = announcementsRepository.getByStatusAndTimePeriod(listStatus, startDate, endDate);

        if (announcements.isEmpty()){
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }
        return announcements;
    }

    public List<Announcements>  getByDateCreated(String startTime, String endTime) throws EmptyWishList, ParseException {
        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse(startTime);
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse(endTime);

        List<Announcements> announcements = announcementsRepository.getByDateCreated(startDate, endDate);

        if (announcements.isEmpty()){
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }
        return announcements;
    }

    public List<Announcements> getByStatus(String status) throws ParseException, EmptyWishList {
        List<String> listStatus = List.of(status.split(","));
        List<Announcements> announcements = announcementsRepository.getByStatus(listStatus);

        if (announcements.isEmpty()){
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }

        return announcements;
    }

    public List<Announcements> findAllFavorites() throws EmptyWishList {
        List<Announcements> announcements = announcementsRepository.findAllFavorites();

        if (announcements.isEmpty()) {
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }

        return announcements;
    }

    public boolean haveThisCategory(int categoryID){
        List<Categories> categoryFromDB = categoryRepository.findAll();
        for (Categories c:categoryFromDB) {
            if (c.getCategory_id() == categoryID){
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
