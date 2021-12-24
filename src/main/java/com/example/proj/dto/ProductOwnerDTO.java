package com.example.proj.dto;

import com.example.proj.model.TechLeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ProductOwnerDTO {
    private Long id;
    private String name;
    private String surname;
    @NonNull
    private String email;
    private Set<TechLeader> techLeaders;

    private ProductOwnerDTO(ProductOwnerDTO.Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.techLeaders = builder.techLeaders;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String surname;
        private String email;
        private Set<TechLeader> techLeaders;

        public ProductOwnerDTO.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public ProductOwnerDTO.Builder name(final String name) {
            this.name = name;
            return this;
        }

        public ProductOwnerDTO.Builder surname(final String surname) {
            this.surname = surname;
            return this;
        }

        public ProductOwnerDTO.Builder email(final String email) {
            this.email = email;
            return this;
        }

        public ProductOwnerDTO.Builder techLeaders(final Set<TechLeader> techLeaders) {
            this.techLeaders = techLeaders;
            return this;
        }

        public ProductOwnerDTO build() {
            return new ProductOwnerDTO(this);
        }
    }
}
