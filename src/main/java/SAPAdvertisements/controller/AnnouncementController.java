package SAPAdvertisements.controller;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.dtos.AnnouncementDto;
import SAPAdvertisements.exeptions.*;
import SAPAdvertisements.models.Announcements;
import SAPAdvertisements.service.AnnouncementService;
import SAPAdvertisements.service.CategoryService;
import SAPAdvertisements.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.announcementService = announcementService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/{announcementNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AnnouncementDto> getAnnouncement(@PathVariable("announcementNumber") String announcementNumber) throws AnnouncementNotFoundException, UserNotFoundException {
            return status(OK).body(convertToAnnouncementsDto(announcementService.readAnnouncement(announcementNumber)));

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Announcements> createAnnouncement(@Valid @RequestBody AnnouncementDto announcementDto) throws InvalidPropertyException, NullPointerException {
           return status(CREATED).body(announcementService.createAnnouncement(convertToAnnouncementsEntity(announcementDto)));
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
            return status(OK).body(convertToAnnouncementsListDto(announcementService.getByStatus(status)));
    }

    @GetMapping(value = "/filter/{status}/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> getByStatusAndTimePeriod(@CurrentSecurityContext(expression = "authentication?.name") String username, @PathVariable("status") String status, @PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) throws EmptyWishList, ParseException, UserNotFoundException {
            Boolean isAdmin = userService.readUser(username).getUserType().equals("ADMIN");
            if (status.toUpperCase().contains("INACTIVE") && !isAdmin) {
                return new ResponseEntity(ConstantMessages.ALLOWED_FOR_ADMIN + ConstantMessages.USER_WHICH_DO_NOT_RIGHT + username, FORBIDDEN);
            }
        return status(OK).body(convertToAnnouncementsListDto(announcementService.getByStatusAndTimePeriod(status,startTime,endTime)));

    }

    @GetMapping(value = "/filter/{startTime}/{endTime}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> getByDateCreated(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) throws EmptyWishList, ParseException, UserNotFoundException {
        return status(OK).body(convertToAnnouncementsListDto(announcementService.getByDateCreated(startTime,endTime)));
    }
    @GetMapping(value = "/fav")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnnouncementDto>> findAllFavorites() throws EmptyWishList, UserNotFoundException {
        return status(OK).body(convertToAnnouncementsListDto(announcementService.findAllFavorites()));
    }

    private AnnouncementDto convertToAnnouncementsDto(Announcements announcements) throws UserNotFoundException {
        AnnouncementDto announcementDto =  modelMapper.map(announcements,AnnouncementDto.class);
        announcementDto.setUsername(userService.readUser(announcements.getUser_id().getUsername()).getUsername());
        return announcementDto;
    }

    private Announcements convertToAnnouncementsEntity(AnnouncementDto announcementDto) {
        Announcements announcements = modelMapper.map(announcementDto,Announcements.class);
        announcements.setCategory_id(categoryService.findCategoryId(announcementDto.getCategoryName()));
        announcements.setUser_id(userService.findUserById(announcementDto.getUsername()));
        return announcements;
    }

    private List<AnnouncementDto> convertToAnnouncementsListDto(List<Announcements> announcements) throws UserNotFoundException {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcements a: announcements) {
            announcementDtos.add(convertToAnnouncementsDto(a));
        }
        return announcementDtos;
    }

}
