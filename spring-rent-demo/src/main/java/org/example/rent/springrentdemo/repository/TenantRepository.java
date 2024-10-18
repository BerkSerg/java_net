package org.example.rent.springrentdemo.repository;

import org.example.rent.springrentdemo.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findAllByEmail(String email);
    Optional<Tenant> findByEmailLike(String partEmail);
}
