package kh.edu.cstad.mbapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountTypeRequest(
        @NotNull(message = "Account type name is required")
        @NotBlank(message = "Account type name must not be blank")
        String name,

        String description
) {
}
