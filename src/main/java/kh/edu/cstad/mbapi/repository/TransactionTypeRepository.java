package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
}
