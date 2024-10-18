package org.example.rent.springrentdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.ScooterDto;
import org.example.rent.springrentdemo.service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TenantController {

    private TenantService tenantService;


    @GetMapping("/tenants")
    public String getAllTenants(Model model) {
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenants";
    }

    @GetMapping("/tenants/{tenantId}/scooters")
    public String getScootersByTenant(@PathVariable("tenantId") Long tenantId, Model model) {
        //model.addAttribute("tenant", tenantService.)
        model.addAttribute("scooters", tenantService.getAllScootersByTenant(tenantId));
        model.addAttribute("freeScooters", tenantService.getAllFreeScooters());
        return "tenants_scooters";
    }

    @PostMapping("/tenants/{tenantId}/reserve-scooter")
    public String reserveScooterByTenant(@PathVariable("tenantId") Long tenantId, ScooterDto scooter) {
        tenantService.reserveScooterToTenant(tenantId, scooter);
        return "redirect:/tenants/" + tenantId + "scooters";
    }

}
