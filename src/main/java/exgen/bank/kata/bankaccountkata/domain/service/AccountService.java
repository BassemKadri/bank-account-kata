package exgen.bank.kata.bankaccountkata.domain.service;

import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

  Transaction deposit(UUID accountId, BigDecimal amount);

  Transaction withdrawal(UUID accountId, BigDecimal amount);

  List<Transaction> history(UUID accountId, Date startDate, Date endDate);
}
