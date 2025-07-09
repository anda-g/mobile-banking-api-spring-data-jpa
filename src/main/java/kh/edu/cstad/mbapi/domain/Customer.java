package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(length = 15, nullable = false)
    private String gender;

    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

//    @OneToOne
//    @JoinColumn(unique = true, nullable = false)
//    private KYC kyc;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    @PrimaryKeyJoinColumn
    private KYC kyc;
}
