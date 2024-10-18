package org.example.rent.springrentdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.rent.springrentdemo.dto.RegistrationDto;
import org.example.rent.springrentdemo.model.Role;
import org.example.rent.springrentdemo.model.Tenant;
import org.example.rent.springrentdemo.repository.TenantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegisterService {
    private final TenantRepository tenantRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerTenant(RegistrationDto registrationDto){
        Tenant newTenant = Tenant.builder()
                .email(registrationDto.getEmail())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();

        tenantRepository.save(newTenant);
    }
}
