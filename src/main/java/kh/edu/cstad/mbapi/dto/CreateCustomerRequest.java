package kh.edu.cstad.mbapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(

        @NotBlank(message = "Full name is required")
        String fullName,
        @NotBlank(message = "Gender is required")
        String gender,
        @Email(message = "Email is not valid")
        String email,
        String phoneNumber,
        String remark,

        @NotNull(message = "National card id is required")
        String nationalCardId,

        @NotNull(message = "Segment is required")
        String segment
) {
}
