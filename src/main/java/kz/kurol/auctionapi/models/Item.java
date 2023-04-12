package kz.kurol.auctionapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.kurol.auctionapi.models.enums.Status;

@Entity
@Table(name = "Items")
public class Item {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_id_items")
    @SequenceGenerator(name = "seq_gen_id_items", sequenceName = "items_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "title",nullable = false)
    @NotEmpty(message = "title should not be empty")
    @Size(min = 2,max = 120, message = "title should be between 2 and 120 characters")
    private String title;

    @Column(name = "description",nullable = false)
    @NotEmpty(message = "description should not be empty")
    @Size(min = 20,max = 250,message = "description should be between 20 and 250 characters")
    private String description;


    @Column(name ="status",nullable = false)
    //@NotEmpty(message = "status should not be empty")
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne //owning side (сторона которая чем то владеет)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Item(){}

    public Item(String title, String description, Status status, Client client) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
