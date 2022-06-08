package exgen.bank.kata.bankaccountkata.domain.repository;

import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

  Transaction save(Transaction transaction);

  Optional<Transaction> findTopByOrderByDateDesc(UUID accountId);
}
