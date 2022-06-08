package exgen.bank.kata.bankaccountkata.domain.factory;

import exgen.bank.kata.bankaccountkata.domain.exceptions.AccountNotFoundException;
import exgen.bank.kata.bankaccountkata.domain.exceptions.BalanceLowException;
import exgen.bank.kata.bankaccountkata.domain.exceptions.NegativeAmountException;
import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;
import exgen.bank.kata.bankaccountkata.domain.repository.AccountRepository;
import exgen.bank.kata.bankaccountkata.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class WithdrawalOperation implements Operation {

  final TransactionRepository transactionRepository;
  final AccountRepository accountRepository;

  public WithdrawalOperation(final TransactionRepository transactionRepository,
      final AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public Transaction operate(UUID accountId, BigDecimal amount) {
    checkNegativeAmount(amount);
    checkExistAccount(accountId);
    BigDecimal lastBalance = getLasBalance(accountId);
    if (lastBalance.compareTo(amount) == 1) {
      Transaction transaction = Transaction.builder()
          .accountId(accountId)
          .amount(amount)
          .type(TransactionType.WITHDRAWL)
          .date(new Date())
          .balance(lastBalance.subtract(amount))
          .build();
      return transactionRepository.save(transaction);
    } else {
      throw BalanceLowException.builder()
          .message("your balance is too low")
          .build();
    }
  }

  private void checkNegativeAmount(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw NegativeAmountException
          .builder().message("Amount" + amount + " can not be negative")
          .build();
    }
  }

  private void checkExistAccount(UUID accountId) {
    accountRepository.getById(accountId)
        .orElseThrow(() -> AccountNotFoundException
            .builder().message("No Account found for accountId " + accountId)
            .build());
  }

  /**
   * Check the first time to get amount transaction.
   *
   * @param accountId the account id
   * @return balance
   */
  private BigDecimal getLasBalance(UUID accountId) {
    return transactionRepository.findTopByOrderByDateDesc(accountId)
        .map(Transaction::getBalance)
        .orElse(new BigDecimal(0));
  }

}
