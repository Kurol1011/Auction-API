package kz.kurol.auctionapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Clients")
public class Client implements UserDetails {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_id_clients")
    @SequenceGenerator(name="seq_gen_id_clients", sequenceName = "clients_id_seq", allocationSize = 1)
    private long id;

    @Column(name="name", nullable = false)
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2,max = 30, message = "Name should be between 2 and 30 characters")
    //@Pattern(regexp = "\b[A-Z][a-z]*\b",message = "Name is not valid")
    private String name;

    @Column(name="surname",nullable = false)
    @NotEmpty(message = "surname should not be empty")
    @Size(min = 2,max = 45, message = "Surname should be between 2 and 30 characters")
    //@Pattern(regexp = "\b[A-Z][a-z]*\b",message = "Name is not valid")
    private String surname;

    @Column(name="email",nullable = false)
    @NotEmpty(message = "email should not be empty")
    @Size(max = 250, message = "email should be less than 250")
    @Email(message = "email should valid")
    private String email;

    @Column(name="password",nullable = false)
    @NotEmpty(message = "password should not be empty")
    @Size(min = 3, max = 250, message = "password should be greater than 3(?) and less than 250 characters")
    private String password;

    @Column(name = "balance")
    @Max(999999999)
    @PositiveOrZero
    private double balance;

    @OneToMany(mappedBy = "client" ) // default fetch = FetchType.Lazy
    @Cascade({org.hibernate.annotations.CascadeType.DELETE})
    private List<Item> items;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "boarditem_client",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "boarditem_id")})
    private List<BoardItem> boardItems;

    public Client(){}

    public Client(String name, String surname, String email, String password, double balance, List<Item> items) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPass(String pass) {
        this.password = pass;
    }
}
