package com.techbuddy.goldendrop.model.repository;

import com.techbuddy.goldendrop.model.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findStoreById(Integer id);

    Optional<Store> findStoreByLicenseId(String licenseId);
}
