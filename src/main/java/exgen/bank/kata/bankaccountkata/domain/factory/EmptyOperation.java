package exgen.bank.kata.bankaccountkata.domain.factory;

import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.UUID;

public class EmptyOperation implements Operation{

  @Override
  public Transaction operate(UUID accountId, BigDecimal amount) {
    return null;
  }
}
