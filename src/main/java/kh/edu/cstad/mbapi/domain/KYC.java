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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; /* uuid */

    @Column(unique = true, nullable = false)
    private String nationalCardId;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Customer customer;

//    @OneToOne
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;


}
