package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.StoreDTO;
import com.techbuddy.goldendrop.exception.InvalidStoreException;
import com.techbuddy.goldendrop.exception.StoreConflictException;
import com.techbuddy.goldendrop.mapper.StoreMapper;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.repository.StoreRepository;
import com.techbuddy.goldendrop.request.StoreRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreDTO create(StoreRequest storeRequest) {
        Optional<Store> existingStore = fetchStoreByLicenseId(storeRequest);
        if (existingStore.isPresent()) {
            throw new StoreConflictException("Store " + "already exists with given license Id");
        }
        Store store = storeRepository.save(storeMapper.map(storeRequest));
        return storeMapper.map(store);
    }

    public Store validateAndFetchStoreId(Long storeId) {
        Optional<Store> store = storeRepository.findStoreById(storeId);
        if (store.isEmpty()) {
            throw new InvalidStoreException("Invalid store id: " + storeId);
        }
        return store.get();
    }

    private Optional<Store> fetchStoreByLicenseId(StoreRequest storeRequest) {
        return storeRepository.findStoreByLicenseId(storeRequest.getLicenseId());
    }
}
