package kz.kurol.auctionapi.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Item item;

    @Column(name = "starting_price",nullable = false)
    @Max(999999999)
    @Min(value = 5, message = "Price should be greater than $5")
    @PositiveOrZero
    private double startingPrice;

    @Column(name = "final_price",nullable = false)
    @Max(999999999)
    @Min(value = 5, message = "final Price should be greater than $5")
    @PositiveOrZero
    private double finalPrice;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Client client;


    public Board() {
    }

    public Board(Item item, double startingPrice, double finalPrice, Client client) {
        this.item = item;
        this.startingPrice = startingPrice;
        this.finalPrice = finalPrice;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
