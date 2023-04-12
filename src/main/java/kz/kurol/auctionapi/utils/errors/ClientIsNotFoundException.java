package kz.kurol.auctionapi.utils.errors;

public class ClientIsNotFoundException extends RuntimeException{
    public ClientIsNotFoundException(String message) {
        super(message);
    }
}
