package SAPAdvertisements.service;

import SAPAdvertisements.validator.Validations;
import SAPAdvertisements.common.ConstantMessages;
import SAPAdvertisements.exeptions.InvalidPropertyException;
import SAPAdvertisements.exeptions.NonUpdateablePropertyException;
import SAPAdvertisements.exeptions.AlreadyExistsException;
import SAPAdvertisements.exeptions.UserNotFoundException;
import SAPAdvertisements.models.Users;
import SAPAdvertisements.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public Users readUser(String username) throws UserNotFoundException {
        Users userToReturn = null;
        List<Users> usersFromDB = usersRepository.findByUsername(username);
        for (Users u : usersFromDB) {
            if (u.getUsername().equals(username)){
                userToReturn = u;
            }
        }
        if (userToReturn == null){
            throw new UserNotFoundException(ConstantMessages.USER_NOT_FOUND_EXCEPTION);
        }

        return userToReturn;
    }

    public Users createUser(Users user) throws InvalidPropertyException, AlreadyExistsException {

        if (!Validations.isValidUsername(user.getUsername())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_USERNAME_EXCEPTION);
        }

        if (registeredUsername(user.getUsername())){
            throw new AlreadyExistsException(ConstantMessages.USERNAME_ALREADY_EXIST_EXCEPTIONS);
        }

        if(!Validations.isValidPassword(user.getPassword())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_PASSWORD_EXCEPTION);
        }

        if (!Validations.isValidEmail(user.getEmail())){
            throw new InvalidPropertyException(ConstantMessages.INVALID_EMAIL_EXCEPTION);
        }
        if (registeredEmail(user.getEmail())){
            throw new AlreadyExistsException(ConstantMessages.EMAIL_ALREADY_EXIST_EXCEPTIONS);
        }

        if (!Validations.isValidPhoneNumber(user.getPhoneNumber())) {
            throw new InvalidPropertyException(ConstantMessages.INVALID_PHONE_NUMBER_EXCEPTION);
        }

        if (!Validations.isValidUserType(user.getUserType())) {
            throw new InvalidPropertyException(ConstantMessages.INVALID_USER_TYPE_EXCEPTION);
        }

          return usersRepository.save(user);
    }

    public Users updateUserData(String username, String field, String data) throws UserNotFoundException, InvalidPropertyException, AlreadyExistsException, NonUpdateablePropertyException {
        Users user = readUser(username);
        switch (field){
            case "username":
                throw new NonUpdateablePropertyException(ConstantMessages.NON_UPDATEABLE_USERNAME_EXCEPTIONS);
            case "password":
                if(!Validations.isValidPassword(data)){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_PASSWORD_EXCEPTION);
                }
                user.setPassword(data);
                break;
            case "email":
                if (!Validations.isValidEmail(data)){
                    throw new InvalidPropertyException(ConstantMessages.INVALID_EMAIL_EXCEPTION);
                }
                if (registeredEmail(data)){
                    throw new AlreadyExistsException(ConstantMessages.EMAIL_ALREADY_EXIST_EXCEPTIONS);
                }
                user.setEmail(data);
                break;
            case "phoneNumber":
                if (!Validations.isValidPhoneNumber(data)) {
                    throw new InvalidPropertyException(ConstantMessages.INVALID_PHONE_NUMBER_EXCEPTION);
                }
                user.setPhoneNumber(data);
                break;
            case "userType":
                if (!Validations.isValidUserType(data.toUpperCase())) {
                    throw new InvalidPropertyException(ConstantMessages.INVALID_USER_TYPE_EXCEPTION);
                }
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
        List<Users> usersFromFile = usersRepository.findByUsername(name);
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
}

