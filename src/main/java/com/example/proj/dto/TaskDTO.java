package com.example.proj.dto;

import com.example.proj.common.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    @NonNull
    private String projectName;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Status status;

    private TaskDTO(Builder builder) {
        this.projectName = builder.projectName;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
    }

    public static class Builder {
        private String projectName;
        private String title;
        private String description;
        private Status status;

        public Builder projectName(final String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public TaskDTO build() {
            return new TaskDTO(this);
        }
    }
}
