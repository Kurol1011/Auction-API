package kz.kurol.auctionapi.dto;

public class AuctionItemDTO {
    private long id;
    private String title;
    private String description;
    private double initialPrice;

    private double finalPrice;

    public AuctionItemDTO() {
    }

    public AuctionItemDTO(String title, String description, double initialPrice) {
        this.title = title;
        this.description = description;
        this.initialPrice = initialPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
