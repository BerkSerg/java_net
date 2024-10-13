package org.stores.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String store_name;
    private String address;
    private String phone;
    private String email;
    private String link;
    private String category;
    private String description;

    public Store() {}

    public Store(String store_name, String address, String phone, String email, String link, String category, String description) {
        this.store_name = store_name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.link = link;
        this.category = category;
        this.description = description;
    }

}
