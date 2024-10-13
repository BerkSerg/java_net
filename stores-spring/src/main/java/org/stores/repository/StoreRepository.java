package org.stores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stores.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
}
