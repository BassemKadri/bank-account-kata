package exgen.bank.kata.bankaccountkata.domain.service;

import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {

  Transaction deposit(UUID accountId, BigDecimal amount);

  Transaction withdrawal(UUID accountId, BigDecimal amount);
}
