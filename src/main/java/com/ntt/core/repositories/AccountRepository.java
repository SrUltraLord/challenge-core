package com.ntt.core.repositories;

import com.ntt.core.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNumber(String number);

    Optional<Account> findByIdAndStatus(Long id, boolean status);

    Optional<Account> findByNumberAndStatus(String number, boolean status);

    List<Account> findAllByClientId(Long clientId);
}
