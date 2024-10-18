package org.example.rent.springrentdemo.repository;

import org.example.rent.springrentdemo.model.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    List<Scooter> findAllByOwner_Id(Long ownerId);
    List<Scooter> findAllByOwnerIsNull();
}
