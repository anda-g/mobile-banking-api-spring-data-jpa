package kh.edu.cstad.mbapi.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String customerPhoneNumber,
        String accountNumber,
        String accountCurrency,
        BigDecimal balance,
        Integer accountTypeId
) {
}
