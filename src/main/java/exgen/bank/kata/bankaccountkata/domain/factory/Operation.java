package exgen.bank.kata.bankaccountkata.domain.factory;

import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.UUID;

public interface Operation {

  Transaction operate(UUID accountId, BigDecimal amount);
}
