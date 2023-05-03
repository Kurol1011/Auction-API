package kz.kurol.auctionapi.utils.errors;

public class JwtTokenHasExpired extends RuntimeException{
    public JwtTokenHasExpired(String msg){
        super(msg);
    }
}
