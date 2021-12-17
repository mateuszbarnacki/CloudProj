package com.example.proj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private String name;
    private String surname;
    @NotNull
    private String email;

    private EmployeeDTO(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
    }

    public static class Builder {
        private String name;
        private String surname;
        private String email;

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder surname(final String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public EmployeeDTO build() {
            return new EmployeeDTO(this);
        }
    }
}
