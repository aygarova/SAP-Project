package SAPAdvertisements.controller;

import SAPAdvertisements.dtos.UserDto;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.NonUpdateablePropertyException;
import SAPAdvertisements.exeptions.AlreadyExistsException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.service.UserService;
import SAPAdvertisements.util.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) throws UserNotFoundException {
            return status(OK).body(Converters.convertToUserDto(userService.readUser(username)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Users> createUser(@Valid @RequestBody UserDto userDto) throws AlreadyExistsException, InvalidPropertyException {
            return status(CREATED).body(userService.createUser(Converters.convertToUserEntity(userDto)));
    }

    @PatchMapping(value = "/{username}/{field}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> patchUserData(@PathVariable("username") String username, @PathVariable("field") String field, @RequestBody String data) throws UserNotFoundException, AlreadyExistsException, InvalidPropertyException, NonUpdateablePropertyException {
          return status(OK).body(userService.updateUserData(username,field,data));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteUser(@RequestBody Users user) {
        userService.deleteUser(user);
        return status(NO_CONTENT).build();
    }
}
