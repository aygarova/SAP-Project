package SAPAdvertisements.service;

import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.NonUpdateablePropertyException;
import SAPAdvertisements.exeptions.AlreadyExistsException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users readUser(String username) throws UserNotFoundException {
        Users usersFromDB = usersRepository.findByUsername(username);
        if (usersFromDB == null){
            throw new UserNotFoundException(ConstantMessages.USER_NOT_FOUND_EXCEPTION);
        }

        return usersFromDB;
    }

    public Users createUser(Users user) throws AlreadyExistsException {
        if (registeredUsername(user.getUsername())){
            throw new AlreadyExistsException(ConstantMessages.USERNAME_ALREADY_EXIST_EXCEPTIONS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (registeredEmail(user.getEmail())){
            throw new AlreadyExistsException(ConstantMessages.EMAIL_ALREADY_EXIST_EXCEPTIONS);
        }

          return usersRepository.save(user);
    }

    public Users updateUserData(String username, String field, String data) throws UserNotFoundException, AlreadyExistsException, NonUpdateablePropertyException {
        Users user = readUser(username);
        switch (field){
            case "username":
                throw new NonUpdateablePropertyException(ConstantMessages.NON_UPDATEABLE_USERNAME_EXCEPTIONS);
            case "password":
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                break;
            case "email":
                if (registeredEmail(data)){
                    throw new AlreadyExistsException(ConstantMessages.EMAIL_ALREADY_EXIST_EXCEPTIONS);
                }
                user.setEmail(data);
                break;
            case "phoneNumber":
                user.setPhoneNumber(data);
                break;
            case "userType":
                user.setUserType(data.toUpperCase());
                break;
        }
        usersRepository.save(user);
        return user;
    }

    public void deleteUser(Users user) {
       usersRepository.deleteById(user.getUser_id());
    }


    public boolean registeredUsername(String name){
        List<Users> usersFromFile = usersRepository.getUserByUsername(name);
        for (Users u:usersFromFile) {
            if (u.getUsername().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean registeredEmail(String email){
        List<Users> usersFromFile = usersRepository.findAll();
        for (Users u:usersFromFile) {
            if (u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public Users findUserById(String username) {
        return usersRepository.findByUsername(username);
    }
}

