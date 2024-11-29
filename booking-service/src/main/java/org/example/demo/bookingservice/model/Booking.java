package org.example.demo.bookingservice.model;

import lombok.*;
import org.example.demo.bookingservice.model.enums.BookingStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    private int rating;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
