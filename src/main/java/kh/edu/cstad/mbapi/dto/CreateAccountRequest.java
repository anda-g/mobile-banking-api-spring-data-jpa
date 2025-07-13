package kh.edu.cstad.mbapi.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Customer phone number is required")
        @Pattern(
                regexp = "^0[1-9][0-9]{7,8}$",
                message = "Invalid phone number format"
        )
        String customerPhoneNumber,

        @NotBlank(message = "Currency is required")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Currency must be a valid 3-letter code"
        )
        String accountCurrency,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be greater than 0")
        BigDecimal balance,

        @NotNull(message = "Account type ID is required")
        @Min(value = 1, message = "Account type ID must be greater than 0")
        Integer accountTypeId
) {
}
