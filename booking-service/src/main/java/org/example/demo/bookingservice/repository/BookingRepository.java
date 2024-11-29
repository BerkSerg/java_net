package org.example.demo.bookingservice.repository;

import org.example.demo.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long propertyId, LocalDate startDate, LocalDate endDate);
}
