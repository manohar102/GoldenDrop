package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.exception.InvalidStoreException;
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
        if(store.isEmpty()) {
            throw new InvalidStoreException("Invalid store id: " + storeId);
        }
        return store.get();
    }
}
