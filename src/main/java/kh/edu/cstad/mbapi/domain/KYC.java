package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KYC {

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id; /* uuid */

    @Column(unique = true, nullable = false)
    private String nationalCardId;

    @Column(nullable = false)
    private Boolean isVerify;

    @Column(nullable = false)
    private Boolean isDeleted;

//    @OneToOne(mappedBy = "kyc")
//    private Customer customer;

    @OneToOne
    @MapsId("id")
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
