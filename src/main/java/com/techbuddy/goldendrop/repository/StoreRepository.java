package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findStoreById(Long id);

    Optional<Store> findStoreByLicenseId(String licenseId);
}
