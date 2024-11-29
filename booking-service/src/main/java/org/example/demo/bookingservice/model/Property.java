package org.example.demo.bookingservice.model;

import lombok.*;
import org.example.demo.bookingservice.model.enums.PropertyStatus;
import org.example.demo.bookingservice.model.enums.PropertyType;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User owner;

    private String title;
    private String description;
    private String city;
    private String country;
    private double rating;

    @Column(name = "price_per_day")
    private double pricePerDay;

    @OneToMany(mappedBy="property")
    private List<Booking> bookings;

}
