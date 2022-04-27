package SAPAdvertisements.enums;

public enum AnnouncementStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;
    AnnouncementStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
