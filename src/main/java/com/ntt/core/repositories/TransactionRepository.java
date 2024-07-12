package com.ntt.core.repositories;

import com.ntt.core.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdAndStatus(Long id, boolean status);

    List<Transaction> findAllByAccountIdInAndPerformedAtBetween(Set<Long> accountIds, LocalDate startDate, LocalDate endDate);
}
