package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);
    List<Customer> findByIsDeletedFalse();

    @Query(value = "SELECT EXISTS (SELECT c FROM Customer c WHERE c.phoneNumber = ?1)")
    boolean isExistsByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE Customer c SET c.isDeleted = TRUE WHERE c.phoneNumber = :phoneNumber")
    void disableByPhoneNumber(String phoneNumber);
}
