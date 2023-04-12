package kz.kurol.auctionapi.utils.errors;

public class BoardIsNotFoundException extends RuntimeException{
    public BoardIsNotFoundException(String message) {
        super(message);
    }
}
