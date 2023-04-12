package kz.kurol.auctionapi.models.enums;

public enum Status {
    INACTIVE("INACTIVE"), ACTIVE("ACTIVE");
    private String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
