package SAPAdvertisements.controller;

import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.NonUpdateablePropertyException;
import SAPAdvertisements.exeptions.AlreadyExistsException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> getUser(@PathVariable("username") String username) {
        try {
            return status(OK).body(userService.readUser(username));
        }catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        try {
            return status(CREATED).body(userService.createUser(user));
        } catch (InvalidPropertyException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }

    }

    @PatchMapping(value = "/{username}/{field}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> patchUserData(@PathVariable("username") String username, @PathVariable("field") String field, @RequestBody String data) {
      try {
          return status(OK).body(userService.updateUserData(username,field,data));
      }catch (UserNotFoundException | InvalidPropertyException | AlreadyExistsException | NonUpdateablePropertyException e){
          return new ResponseEntity(e.getMessage(), BAD_REQUEST);
      }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteUser(@RequestBody Users user) {
        userService.deleteUser(user);
        return status(NO_CONTENT).build();
    }
}
