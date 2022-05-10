package SAPAdvertisements.controller;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.dtos.AnnouncementDto;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.util.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CategoryService categoryService;
    private final UserService userService;


    @Autowired
    public AnnouncementController(AnnouncementService announcementService, CategoryService categoryService, UserService userService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping(value = "/{announcementNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AnnouncementDto> getAnnouncement(@PathVariable("announcementNumber") String announcementNumber) throws AnnouncementNotFoundException, UserNotFoundException {
            return status(OK).body(Converters.convertToAnnouncementsDto(announcementService.readAnnouncement(announcementNumber),userService));

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> createAnnouncement(@Valid @RequestBody AnnouncementDto announcementDto) throws InvalidPropertyException, NullPointerException {
           return status(CREATED).body(announcementService.createAnnouncement(Converters.convertToAnnouncementsEntity(announcementDto, userService,categoryService)));
    }


    @PatchMapping(value = "/{announcementNumber}/{field}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> patchAnnouncementData(@PathVariable("announcementNumber") String announcementNumber, @PathVariable("field") String field, @RequestBody String data) throws AnnouncementNotFoundException, InvalidPropertyException, NonUpdateablePropertyException {
            return status(OK).body(announcementService.updateAnnouncement(announcementNumber,field,data));
    }

    @PatchMapping(value = "/inactive/{announcementNumber}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> patchAnnouncementDataInactive(@PathVariable("announcementNumber") String announcementNumber) throws AnnouncementNotFoundException, InvalidPropertyException, NonUpdateablePropertyException {
            return status(OK).body(announcementService.inactiveAnnouncement(announcementNumber));
    }

    @DeleteMapping(value = "{announcementNumber}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteAnnouncement(@PathVariable String announcementNumber) {
        announcementService.deleteAnnouncement(announcementNumber);
        return status(NO_CONTENT).build();
    }

    @GetMapping(value = "/filter/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> getByStatus(@CurrentSecurityContext(expression = "authentication?.name") String username, @PathVariable("status") String status) throws EmptyWishList, ParseException, UserNotFoundException {
            Boolean isAdmin = userService.readUser(username).getUserType().equals("ADMIN");
            if (status.toUpperCase().contains("INACTIVE") && !isAdmin) {
                return new ResponseEntity(ConstantMessages.ALLOWED_FOR_ADMIN + ConstantMessages.USER_WHICH_DO_NOT_RIGHT + username, FORBIDDEN);
            }
            return status(OK).body(Converters.convertToAnnouncementsListDto(announcementService.getByStatus(status),userService));
    }

    @GetMapping(value = "/filter/{status}/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> getByStatusAndTimePeriod(@CurrentSecurityContext(expression = "authentication?.name") String username, @PathVariable("status") String status, @PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) throws EmptyWishList, ParseException, UserNotFoundException {
            Boolean isAdmin = userService.readUser(username).getUserType().equals("ADMIN");
            if (status.toUpperCase().contains("INACTIVE") && !isAdmin) {
                return new ResponseEntity(ConstantMessages.ALLOWED_FOR_ADMIN + ConstantMessages.USER_WHICH_DO_NOT_RIGHT + username, FORBIDDEN);
            }
        return status(OK).body(Converters.convertToAnnouncementsListDto(announcementService.getByStatusAndTimePeriod(status,startTime,endTime),userService));

    }

    @GetMapping(value = "/filter/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> getByDateCreated(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) throws EmptyWishList, ParseException, UserNotFoundException {
        return status(OK).body(Converters.convertToAnnouncementsListDto(announcementService.getByDateCreated(startTime,endTime),userService));
    }
    @GetMapping(value = "/fav")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> findAllFavorites() throws EmptyWishList, UserNotFoundException {
        return status(OK).body(Converters.convertToAnnouncementsListDto(announcementService.findAllFavorites(),userService));
    }
}
