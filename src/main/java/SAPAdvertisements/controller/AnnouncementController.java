package SAPAdvertisements.controller;


import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/{announcementNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Announcements> getAnnouncement(@PathVariable("announcementNumber") String announcementNumber) {
        try {
            return status(OK).body(announcementService.readAnnouncement(announcementNumber));
        } catch (AnnouncementNotFoundException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> createAnnouncement(@RequestBody Announcements announcement) {
       try {
           return status(CREATED).body(announcementService.createAnnouncement(announcement));
       } catch (InvalidPropertyException e) {
           return new ResponseEntity(e.getMessage(), BAD_REQUEST);
       }
    }


    @PatchMapping(value = "/{announcementNumber}/{field}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> patchAnnouncementData(@PathVariable("announcementNumber") String announcementNumber, @PathVariable("field") String field, @RequestBody String data) {
        try {
            return status(OK).body(announcementService.updateAnnouncement(announcementNumber,field,data));
        } catch (AnnouncementNotFoundException | NonUpdateablePropertyException | InvalidPropertyException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @PatchMapping(value = "/inactive/{announcementNumber}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> patchAnnouncementDataInactive(@PathVariable("announcementNumber") String announcementNumber) {
        try {
            return status(OK).body(announcementService.inactiveAnnouncement(announcementNumber));
        } catch (AnnouncementNotFoundException | NonUpdateablePropertyException | InvalidPropertyException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "{announcementNumber}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteAnnouncement(@RequestBody Announcements announcement) {
        announcementService.deleteAnnouncement(announcement);
        return status(NO_CONTENT).build();
    }

    @GetMapping(value = "/filter/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Announcements>> getByStatus(@CurrentSecurityContext(expression = "authentication?.name") String username, @PathVariable("status") String status) {
        try {
            Boolean isAdmin = username.equals("SAPProjectAdmin");
            if (status.toUpperCase().contains("INACTIVE") && !isAdmin) {
                return new ResponseEntity(ConstantMessages.ALLOWED_FOR_ADMIN + ConstantMessages.USER_WHICH_DO_NOT_RIGHT + username, FORBIDDEN);
            }
            return status(OK).body(announcementService.getByStatus(status));
        }catch (ParseException | EmptyWishList e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @GetMapping(value = "/filter/{status}/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Announcements>> getByStatusAndTimePeriod(@CurrentSecurityContext(expression = "authentication?.name") String username, @PathVariable("status") String status, @PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) {
        try {
            Boolean isAdmin = username.equals("SAPProjectAdmin");
            if (status.toUpperCase().contains("INACTIVE") && !isAdmin) {
                return new ResponseEntity(ConstantMessages.ALLOWED_FOR_ADMIN + ConstantMessages.USER_WHICH_DO_NOT_RIGHT + username, FORBIDDEN);
            }
            return status(OK).body(announcementService.getByStatusAndTimePeriod(status,startTime,endTime));
        }catch (ParseException | EmptyWishList e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @GetMapping(value = "/filter/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Announcements>> getByDateCreated(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) {
        try {
            return status(OK).body(announcementService.getByDateCreated(startTime,endTime));
        }catch (ParseException | EmptyWishList e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }
    @GetMapping(value = "/fav")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Announcements>> findAllFavorites() {
        try {
            return status(OK).body(announcementService.findAllFavorites());
        } catch (EmptyWishList e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }

}
