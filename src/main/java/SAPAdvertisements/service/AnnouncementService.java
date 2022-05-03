package SAPAdvertisements.service;

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
        if (isSameAnnouncementNumber(announcement.getAnnouncementNumber())){
            throw new InvalidPropertyException(ConstantMessages.SAME_ANNOUNCEMENT_NUMBER);
        }

        if (!haveThisCategory(announcement.getCategory_id().getCategory_id())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
        }

        if (!haveThisUserById(announcement.getUser_id().getUser_id())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_USERNAME_EXCEPTION);
        }

        return announcementsRepository.save(announcement);
    }


    public Announcements updateAnnouncement(String announcementNumber, String field, String data) throws AnnouncementNotFoundException, NonUpdateablePropertyException, InvalidPropertyException {
        Announcements announcement = readAnnouncement(announcementNumber);
        switch (field){
            case "announcementName":
                announcement.setAnnouncementName(data);
                break;
            case "announcementNumber":
                throw new NonUpdateablePropertyException(ConstantMessages.NON_UPDATEABLE_ANNOUNCEMENT_NUMBER_EXCEPTIONS);
            case "price":
                announcement.setPrice(Double.parseDouble(data));
                break;
            case "descriptions":
                announcement.setDescriptions(data);
                break;
            case "status":
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

                announcement.setCategory_id(new Categories(Integer.parseInt(data)));

                break;
            case "userID":
                try {
                    if (!haveThisUserById(Integer.parseInt(data))){
                        throw new InvalidPropertyException(ConstantMessages.INVALID_USERNAME_EXCEPTION);
                    }
                }catch (NumberFormatException e){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_CATEGORY_EXCEPTION);
                }

                announcement.setUser_id(new Users(Integer.parseInt(data)));

                break;
        }
        announcementsRepository.save(announcement);
        return announcement;
    }

    public void deleteAnnouncement(String announcementNumber) {
        announcementsRepository.deleteById(announcementsRepository.getAnnouncementsByNumber(announcementNumber).getAnnouncement_id());
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
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
        List<String> listStatus = List.of(status.split(","));
        List<Announcements> announcements = announcementsRepository.getByStatusAndTimePeriod(listStatus, startDate, endDate);

        if (announcements.isEmpty()){
            throw new EmptyWishList(ConstantMessages.LIST_IS_EMPTY);
        }
        return announcements;
    }

    public List<Announcements>  getByDateCreated(String startTime, String endTime) throws EmptyWishList, ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

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

    public Announcements findAnnouncementsIdNumber(String announcementNumber) {
        return announcementsRepository.getAnnouncementsByNumber(announcementNumber);

    }
}
