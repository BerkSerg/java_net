package org.example.rent.springrentdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.TenantDto;
import org.example.rent.springrentdemo.service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final TenantService tenantService;

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/admin";
    }

    @PostMapping("/admin/search")
    @ResponseBody
    public List<TenantDto> searchTenant(@RequestParam String partEmail) {
        return tenantService.getTenantsByEmail(partEmail);
    }

}
