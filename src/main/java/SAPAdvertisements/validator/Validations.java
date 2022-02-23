package SAPAdvertisements.validator;

import SAPAdvertisements.enums.AnnouncementStatus;
import SAPAdvertisements.enums.UserType;
import SAPAdvertisements.common.ConstantMessages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    public static boolean isValidUsername(String username) {
        String regexUsername = "^[A-Za-z]\\w{4,29}$";
        Pattern pattern = Pattern.compile(regexUsername);

        if (username == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$";
        Pattern pattern = Pattern.compile(regexPassword);

        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(regexEmail);

        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email.toLowerCase(Locale.ROOT));

        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String number) {
        boolean flag = false;
        if (number == null){
            return false;
        }
        for (Character ch : number.toCharArray()) {
            if (Character.isDigit(ch)) {
                flag = true;
            }
        }
        if (flag) {
            if (number.length() == 10) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidUserType(String userType) {
        if(userType == null){
            return false;
        }
        if (userType.equals(UserType.USER.getType()) || userType.equals(UserType.ADMINISTRATOR.getType())) {
            return true;
        }


        return false;
    }

    public static boolean isValidAnnouncementName(String announcementName) {
        if (announcementName.equals("")){
            return false;
        }
        if (announcementName.length() <= 30) {
            return true;
        }
        return false;
    }

    public static boolean isValidPrice(double price) {
        if (price > 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidAnnouncementNumber(String announcementNumber) {
        String regexItemNumber = "[a-zA-Z0-9]{3,20}";
        Pattern pattern = Pattern.compile(regexItemNumber);

        if (announcementNumber == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(announcementNumber);

        return matcher.matches();
    }

    public static boolean isValidDescriptions(String descriptions) {
        String regexDescriptions = "^(.|\\s)*[a-zA-Z]+(.|\\s)*$";
        Pattern pattern = Pattern.compile(regexDescriptions);

        if (descriptions == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(descriptions);

        return matcher.matches();
    }

     public static boolean isValidAnnouncementStatus(String announcementStatus) {
        if (announcementStatus.equals("")){
            return false;
        }

        if (announcementStatus.equals(AnnouncementStatus.ACTIVE.getStatus()) || announcementStatus.equals(AnnouncementStatus.INACTIVE.getStatus())) {
            return true;
        }
        return false;
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date testDate = null;
        try {
            testDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println(ConstantMessages.INVALID_DATA_FORMAT_EXCEPTION);
            return false;
        }
        if (simpleDateFormat.format(testDate).equals(date)){
            return true;
        }
        return false;
    }

    public static boolean isDateToIsAfterDateFrom(String dateFrom, String dateTo){
        try {
            Date dateF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateFrom);
            Date dateT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateTo);
            if (dateF.before(dateT)){
                return true;
            }
        } catch (ParseException e) {
            System.out.println(ConstantMessages.INVALID_DATA_FORMAT_EXCEPTION);
        }
       return false;
    }
}
