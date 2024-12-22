package org.example.demo.bookingservice.repository;


import org.example.demo.bookingservice.model.Property;
import org.example.demo.bookingservice.model.PropertyPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<PropertyPhotos, Long> {
    PropertyPhotos findFirstByProperty(Property property);
    List<PropertyPhotos> findAllByProperty(Property property);

}
