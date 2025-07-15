package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(unique = true, nullable = false, length = 16, updatable = false)
    private String accountNumber;

    @Column(nullable = false, length = 16)
    private String accountCurrency;

    @Column(nullable = false, columnDefinition = "DECIMAL", precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @Column(nullable = false, columnDefinition = "DECIMAL", precision = 15, scale = 2)
    private BigDecimal overLimit;

    @Column
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> senderTransactions;

    @Column
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    List<Transaction> receiverTransactions;

}
