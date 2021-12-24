package com.example.proj.mapper;

import com.example.proj.dto.ProductOwnerDTO;
import com.example.proj.model.ProductOwner;
import org.springframework.stereotype.Component;

@Component
public final class ProductOwnerMapper {
    private ProductOwnerMapper() {
    }

    public ProductOwner map(ProductOwnerDTO productOwnerDTO) {
        ProductOwner entity = new ProductOwner();

        entity.setId(productOwnerDTO.getId());
        entity.setName(productOwnerDTO.getName());
        entity.setSurname(productOwnerDTO.getSurname());
        entity.setEmail(productOwnerDTO.getEmail());
        entity.setTechLeaders(productOwnerDTO.getTechLeaders());
        return entity;
    }

    public ProductOwnerDTO map(ProductOwner entity) {
        return new ProductOwnerDTO.Builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .techLeaders(entity.getTechLeaders())
                .build();
    }
}
