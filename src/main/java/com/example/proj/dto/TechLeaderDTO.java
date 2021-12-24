package com.example.proj.dto;

import com.example.proj.model.Developer;
import com.example.proj.model.ProductOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TechLeaderDTO {
    private Long id;
    private String name;
    private String surname;
    @NonNull
    private String email;
    private Set<Developer> developers;
    private Set<ProductOwner> productOwners;

    private TechLeaderDTO(TechLeaderDTO.Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.developers = builder.developers;
        this.productOwners = builder.productOwners;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String surname;
        private String email;
        private Set<Developer> developers;
        private Set<ProductOwner> productOwners;

        public TechLeaderDTO.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public TechLeaderDTO.Builder name(final String name) {
            this.name = name;
            return this;
        }

        public TechLeaderDTO.Builder surname(final String surname) {
            this.surname = surname;
            return this;
        }

        public TechLeaderDTO.Builder email(final String email) {
            this.email = email;
            return this;
        }

        public TechLeaderDTO.Builder developers(final Set<Developer> developers) {
            this.developers = developers;
            return this;
        }

        public TechLeaderDTO.Builder productOwners(final Set<ProductOwner> productOwners) {
            this.productOwners = productOwners;
            return this;
        }

        public TechLeaderDTO build() {
            return new TechLeaderDTO(this);
        }
    }
}
