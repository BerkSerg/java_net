package org.example.stores.service;

import org.example.stores.entity.Store;
import org.example.stores.repository.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoresRepository storesRepository;

    public Store createStore(String store_name, String address, String phone, String email, String link, String category, String description){
        return new Store(store_name, address, phone, email, link, category, description);
    }

    public void save(Store stores) {
        storesRepository.save(stores);
    }

    public List<Store> getStoreList() {
        return storesRepository.findAll();
    }

    public Store getStoreById(int id){
        Optional<Store> store = storesRepository.findById(id);
        return store.orElseGet(store::get);
    }

    public void delete(Store store) {
        storesRepository.delete(store);
    }

}
