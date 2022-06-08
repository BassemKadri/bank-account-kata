package exgen.bank.kata.bankaccountkata.domain.service.impl;

import exgen.bank.kata.bankaccountkata.domain.factory.Operation;
import exgen.bank.kata.bankaccountkata.domain.factory.OperationFactory;
import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;
import exgen.bank.kata.bankaccountkata.domain.service.AccountService;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {

  private OperationFactory operationFactory;

  @Override
  public Transaction deposit(final UUID accountId, final BigDecimal amount) {
    Operation op = operationFactory.getOperation(TransactionType.DEPOSIT);
    return op.operate(accountId, amount);
  }

  @Override
  public Transaction withdrawal(final UUID accountId,final BigDecimal amount) {
    Operation op = operationFactory.getOperation(TransactionType.WITHDRAWL);
    return op.operate(accountId, amount);
  }

}
