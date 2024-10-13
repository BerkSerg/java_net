package org.stores.service;

import org.springframework.stereotype.Service;
import org.stores.entity.Store;
import org.stores.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public Store createStore(String store_name, String address, String phone, String email, String link, String category, String description){
        return new Store(store_name, address, phone, email, link, category, description);
    }

    public void save(Store stores) {
        storeRepository.save(stores);
    }

    public List<Store> getStoreList() {
        return storeRepository.findAll();
    }

    public Store getStoreById(int id){
        Optional<Store> store = storeRepository.findById(id);
        return store.orElseGet(store::get);
    }

    public void delete(Store store) {
        storeRepository.delete(store);
    }

}
