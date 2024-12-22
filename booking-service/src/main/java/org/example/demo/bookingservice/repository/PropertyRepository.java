package org.example.demo.bookingservice.repository;

import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.enums.BookingStatus;
import org.example.demo.bookingservice.model.enums.PropertyStatus;
import org.example.demo.bookingservice.model.enums.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findAllByPropertyStatusOrderByIdDesc(PropertyStatus propertyStatus, Pageable pageable);

    @Query("SELECT p FROM Property p WHERE "+
            "(:propertyType IS NULL OR p.propertyType=:propertyType) AND " +
            "(:title IS NULL OR p.title LIKE %:title%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%) AND " +
            "(:city IS NULL OR p.city=:city) AND " +
            "(:rating IS NULL OR p.rating>=:rating) AND " +
            "(:priceFrom IS NULL OR p.pricePerDay>=:priceFrom) AND " +
            "(:priceTo IS NULL OR p.pricePerDay<=:priceTo) AND " +
            "(:propertyStatus IS NULL OR p.propertyStatus=:propertyStatus)"
    )
    Page<Property> searchByParameters(@Param("propertyType") String propertyType,
                                      @Param("title") String title,
                                      @Param("description") String description,
                                      @Param("city") String city,
                                      @Param("rating") double rating,
                                      @Param("propertyStatus") PropertyStatus propertyStatus,
                                      @Param("priceFrom") Double priceFrom,
                                      @Param("priceTo") Double priceTo,
                                      Pageable pageable
                                      );


    @Query("SELECT DISTINCT p FROM Property p WHERE " +
            "(:propertyType IS NULL OR p.propertyType=:propertyType) AND " +
            "(:title IS NULL OR p.title LIKE %:title%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%) AND " +
            "(:city IS NULL OR p.city=:city) AND " +
            "(CAST(:rating AS integer) IS NULL OR p.rating IS NULL OR p.rating>=:rating) AND " +
            "(:priceFrom IS NULL OR p.pricePerDay>=:priceFrom) AND " +
            "(:priceTo IS NULL OR p.pricePerDay<=:priceTo) AND " +
            "(:propertyStatus IS NULL OR p.propertyStatus=:propertyStatus) " +
            "AND (CAST(:startDate AS date) IS NULL OR CAST(:endDate AS date) IS NULL OR " +
            " NOT EXISTS (SELECT b FROM Booking b WHERE b.property=p AND " +
            " b.startDate <= :endDate AND b.endDate >= :startDate AND b.status IN (:st1, :st2))) ORDER BY p.id DESC"
    )
    Page<Property> findAllByParametersAndDates(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("propertyType") PropertyType propertyType,
        @Param("title") String title,
        @Param("description") String description,
        @Param("city") String city,
        @Param("rating") double rating,
        @Param("propertyStatus") PropertyStatus propertyStatus,
        @Param("priceFrom") Double priceFrom,
        @Param("priceTo") Double priceTo,
        @Param("st1") BookingStatus status1,
        @Param("st2") BookingStatus status2,
        Pageable pageable
    );

}

