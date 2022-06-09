package exgen.bank.kata.bankaccountkata.domain.service.impl;

import exgen.bank.kata.bankaccountkata.domain.factory.Operation;
import exgen.bank.kata.bankaccountkata.domain.factory.OperationFactory;
import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;
import exgen.bank.kata.bankaccountkata.domain.repository.TransactionRepository;
import exgen.bank.kata.bankaccountkata.domain.service.AccountService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountServiceImpl implements AccountService {

  final OperationFactory operationFactory;
  final TransactionRepository transactionRepository;

  public AccountServiceImpl(
      final OperationFactory operationFactory,
      final TransactionRepository transactionRepository) {
    this.operationFactory = operationFactory;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction deposit(final UUID accountId, final BigDecimal amount) {
    Operation op = operationFactory.getOperation(TransactionType.DEPOSIT);
    return op.operate(accountId, amount);
  }

  @Override
  public Transaction withdrawal(final UUID accountId, final BigDecimal amount) {
    Operation op = operationFactory.getOperation(TransactionType.WITHDRAWL);
    return op.operate(accountId, amount);
  }

  @Override
  public List<Transaction> history(final UUID accountId, final Date startDate, final Date endDate) {
    List<Transaction> transactions = transactionRepository
        .findByDateBetween(accountId, startDate, endDate);
    logHistoryAccount(transactions);
    return transactions;
  }

  /**
   * Log history account.
   *
   * @param transactions transactions to log
   * @return log message
   */
  public String logHistoryAccount(List<Transaction> transactions) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%-12s", "TransactionType"));
    sb.append(",");
    sb.append(String.format("%-35s", "Date"));
    sb.append(",");
    sb.append(String.format("%-10s", "Amount"));
    sb.append(",");
    sb.append(String.format("%-20s", "Balance") + "\n");
    for (Transaction transaction : transactions) {
      sb.append(String.format("%-12s", transaction.getType().name()));
      sb.append(String.format("%33s", transaction.getDate().toString()));
      sb.append(String.format("%11s", transaction.getAmount()));
      sb.append(String.format("%10s", transaction.getBalance()));
      sb.append("\n");
    }
    log.info(sb.toString());
    return sb.toString();
  }


}
