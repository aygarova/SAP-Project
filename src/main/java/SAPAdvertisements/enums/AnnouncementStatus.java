package SAPAdvertisements.enums;

public enum AnnouncementStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String status;
    AnnouncementStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
