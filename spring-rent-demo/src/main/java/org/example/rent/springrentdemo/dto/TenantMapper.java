package org.example.rent.springrentdemo.dto;

import lombok.experimental.UtilityClass;
import org.example.rent.springrentdemo.model.Tenant;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TenantMapper {
    public static List<TenantDto> toDtoList(List<Tenant> all) {
        all.stream().map(TenantMapper::toDto).collect(Collectors.toList());
        return null;
    }

    public static TenantDto toDto(Tenant tenant){
        return TenantDto.builder()
                .id(tenant.getId())
                .email(tenant.getEmail())
                .build();
    }
}
