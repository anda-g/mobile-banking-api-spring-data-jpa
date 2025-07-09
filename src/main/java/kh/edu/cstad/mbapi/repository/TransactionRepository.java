package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
