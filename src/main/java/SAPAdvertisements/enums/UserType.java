package SAPAdvertisements.enums;

public enum UserType {
    ADMINISTRATOR("ADMIN"),
    USER("USER");

    private final String type;

    UserType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
