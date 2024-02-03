package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.controller.request.StoreRequest;
import com.techbuddy.goldendrop.controller.response.StoreResponse;
import com.techbuddy.goldendrop.exception.InvalidStoreException;
import com.techbuddy.goldendrop.exception.StoreConflictException;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.repository.StoreRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store fetchStoreById(Integer storeId) {
        Optional<Store> store = storeRepository.findStoreById(storeId);
        if (store.isEmpty()) {
            throw new InvalidStoreException("Invalid store id: " + storeId);
        }
        return store.get();
    }

    public StoreResponse create(StoreRequest storeRequest) {
        Optional<Store> existingStore = fetchStoreByLisenceId(storeRequest);
        if (existingStore.isPresent()) {
            throw new StoreConflictException("Store " + "already exixts with given license Id");
        }
        Store store = storeRepository.save(Store.buildFromRequest(storeRequest));
        return StoreResponse.buildFromEntity(store);
    }

    private Optional<Store> fetchStoreByLisenceId(StoreRequest storeRequest) {
        return storeRepository.findStoreByLicenseId(storeRequest.getLicenseId());
    }
}
