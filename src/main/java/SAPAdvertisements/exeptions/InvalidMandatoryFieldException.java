package SAPAdvertisements.exeptions;

public class InvalidMandatoryFieldException extends Exception{
    public InvalidMandatoryFieldException(String message) {
        super(message);
    }
}
