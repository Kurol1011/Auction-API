package kz.kurol.auctionapi.utils.errors;

public class ItemIsNotFoundException extends RuntimeException{
    public ItemIsNotFoundException(String message) {
        super(message);
    }
}
