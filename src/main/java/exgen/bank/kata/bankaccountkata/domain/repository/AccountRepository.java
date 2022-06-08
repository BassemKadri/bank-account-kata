package exgen.bank.kata.bankaccountkata.domain.repository;

import exgen.bank.kata.bankaccountkata.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

  Optional<Account> getById(UUID accountId);
}
