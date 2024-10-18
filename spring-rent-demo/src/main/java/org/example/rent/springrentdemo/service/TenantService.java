package org.example.rent.springrentdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.ScooterDto;
import org.example.rent.springrentdemo.dto.ScooterMapper;
import org.example.rent.springrentdemo.dto.TenantDto;
import org.example.rent.springrentdemo.dto.TenantMapper;
import org.example.rent.springrentdemo.model.Scooter;
import org.example.rent.springrentdemo.model.Tenant;
import org.example.rent.springrentdemo.repository.ScooterRepository;
import org.example.rent.springrentdemo.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final ScooterRepository scooterRepository;

    public List<TenantDto> getAllTenants() {
        return TenantMapper.toDtoList(tenantRepository.findAll());
    }

    public List<ScooterDto> getAllScootersByTenant(Long tenantId){
        return ScooterMapper.toDtoList(scooterRepository.findAllByOwner_Id(tenantId));
    }

    public List<ScooterDto> getAllFreeScooters(){
        return ScooterMapper.toDtoList(scooterRepository.findAllByOwnerIsNull());
    }

    @Transactional
    public void reserveScooterToTenant(Long tenantId, ScooterDto scooter){
        Tenant client = tenantRepository.getById(tenantId);
        Scooter freeScooter = scooterRepository.getById(scooter.getId());
        freeScooter.setOwner(client);
        scooterRepository.save(freeScooter);
    }

    public List<TenantDto> getTenantsByEmail(String partEmail){
        return  TenantMapper.toDtoList(tenantRepository.findAllByEmail(partEmail));
    }
}
