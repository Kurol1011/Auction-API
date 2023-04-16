package kz.kurol.auctionapi.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_item")
public class BoardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "starting_price",nullable = false)
    @Max(999999999)
    @Min(value = 5, message = "Price should be greater than $5")
    @PositiveOrZero
    private double startingPrice;

    @Column(name = "final_price")
    @Max(999999999)
    //@PositiveOrZero
    private double finalPrice;

    @ManyToMany(mappedBy = "boardItems")
    private List<Client> participants;


    public BoardItem() {
    }

    public BoardItem( Item item, double startingPrice, double finalPrice, List<Client> participants) {
        this.item = item;
        this.startingPrice = startingPrice;
        this.finalPrice = finalPrice;
        this.participants = participants;
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

    public List<Client> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Client> participants) {
        this.participants = participants;
    }


}
