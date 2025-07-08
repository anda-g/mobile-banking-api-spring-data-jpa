package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(unique = true, nullable = false, length = 16, updatable = false)
    private String accountNumber;

    @Column(nullable = false, length = 16)
    private String accountType;

    @Column(nullable = false, length = 16)
    private String accountCurrency;

    @Column(nullable = false, columnDefinition = "MONEY")
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean isDeleted;

}
