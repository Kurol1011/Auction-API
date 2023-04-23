package kz.kurol.auctionapi.dto;

public class RateDTO {
    private long id;
    private double rate;

    public RateDTO() {
    }

    public RateDTO(long id, double rate) {
        this.id = id;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
