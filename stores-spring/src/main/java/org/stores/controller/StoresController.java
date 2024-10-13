package org.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.stores.entity.Store;
import org.stores.service.StoreService;

import java.util.List;

@Controller
public class StoresController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @GetMapping("/stores-list")
    public String storesListController(Model model) {
        List<Store> storesList = storeService.getStoreList();
        model.addAttribute("storesList", storesList);
        return "stores-list";
    }

    @GetMapping("/store-add")
    public String storesAddPage() {
        return "store-add";
    }

    @PostMapping("/store-add-new")
    public String storesAddStore(
            @RequestParam(name = "store_name") String storeName,
            @RequestParam(name = "address") String storeAddress,
            @RequestParam(name = "phone") String storePhone,
            @RequestParam(name = "email") String storeEmail,
            @RequestParam(name = "link") String storeLink,
            @RequestParam(name = "category") String storeCategory,
            @RequestParam(name = "description") String storeDesc,
            Model model) {

        Store store = storeService.createStore(storeName, storeAddress, storePhone, storeEmail, storeLink, storeCategory, storeDesc);
        storeService.save(store);
        model.addAttribute("store_name", store.getStore_name());
        return "index";
    }

    @GetMapping("/store-edit")
    public String storesEditPage(@RequestParam(name = "id", required = true, defaultValue = "0") String idReq, Model model) {
        int id = Integer.parseInt(idReq);
        Store store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "store-edit";
    }

    @PostMapping("/store-save")
    public String storesSave(
            @RequestParam(name = "id") String idReq,
            @RequestParam(name = "store_name") String storeName,
            @RequestParam(name = "address") String storeAddress,
            @RequestParam(name = "phone") String storePhone,
            @RequestParam(name = "email") String storeEmail,
            @RequestParam(name = "link") String storeLink,
            @RequestParam(name = "category") String storeCategory,
            @RequestParam(name = "description") String storeDesc,
            Model model) {
        int id = Integer.parseInt(idReq);
        Store store = storeService.getStoreById(id);
        store.setStore_name(storeName);
        store.setAddress(storeAddress);
        store.setPhone(storePhone);
        store.setEmail(storeEmail);
        store.setLink(storeLink);
        store.setCategory(storeCategory);
        store.setDescription(storeDesc);
        storeService.save(store);
        List<Store> storesList = storeService.getStoreList();
        model.addAttribute("storesList", storesList);
        return "stores-list";
    }

    @GetMapping("/store-del")
    public String storeDeleteController(@RequestParam(name = "id", required = true, defaultValue = "0") String idReq, Model model) {
        int id = Integer.parseInt(idReq);
        Store store = storeService.getStoreById(id);
        storeService.delete(store);
        List<Store> storesList = storeService.getStoreList();
        model.addAttribute("storesList", storesList);
        return "stores-list";
    }

}
