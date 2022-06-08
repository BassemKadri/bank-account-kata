package exgen.bank.kata.bankaccountkata.domain.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import exgen.bank.kata.bankaccountkata.domain.exceptions.AccountNotFoundException;
import exgen.bank.kata.bankaccountkata.domain.exceptions.BalanceLowException;
import exgen.bank.kata.bankaccountkata.domain.exceptions.NegativeAmountException;
import exgen.bank.kata.bankaccountkata.domain.model.Account;
import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import exgen.bank.kata.bankaccountkata.domain.repository.AccountRepository;
import exgen.bank.kata.bankaccountkata.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WithdrawalOperationTest {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private AccountRepository accountRepository;

  private WithdrawalOperation withdrawalOperation;

  @BeforeEach
  void setup() {
    this.withdrawalOperation = new WithdrawalOperation(transactionRepository, accountRepository);
  }

  @Test
  public void Should_GetNegativeAmountException_When_WithdrawalNegativeAmount() {
    // Given
    UUID accountId = UUID.randomUUID();

    // When
    assertThrows(NegativeAmountException.class,
        () -> withdrawalOperation.operate(accountId, new BigDecimal(-6)));

    // Then
    verifyNoMoreInteractions(accountRepository);
    verifyNoMoreInteractions(transactionRepository);
  }

  @Test
  public void Should_GetAccountNotFoundException_When_WithdrawalWithUnknownAccountId() {
    // Given
    UUID accountId = UUID.randomUUID();

    // When
    assertThrows(AccountNotFoundException.class,
        () -> withdrawalOperation.operate(accountId, new BigDecimal(234)));

    // Then
    verify(accountRepository, times(1)).getById(accountId);
    verifyNoMoreInteractions(transactionRepository);
  }

  @Test
  public void Should_GetBalanceLowException_When_BalanceIsLessThenBalance() {
    // Given
    UUID accountId = UUID.randomUUID();

    when(accountRepository.getById(accountId)).thenReturn(Optional.of(
        Account.builder()
            .accountId(accountId)
            .name("name")
            .build()
    ));

    when(transactionRepository.findTopByOrderByDateDesc(accountId)).thenReturn(Optional.of(
        Transaction.builder()
            .accountId(accountId)
            .amount(new BigDecimal(450))
            .balance(new BigDecimal(500))
            .build()
    ));

    // When
    assertThrows(BalanceLowException.class,
        () -> withdrawalOperation.operate(accountId, new BigDecimal(600)));

    // Then
    verify(accountRepository, times(1)).getById(accountId);
    verify(transactionRepository, times(1)).findTopByOrderByDateDesc(accountId);
  }

  @Test
  public void Should_WithdrawalAmount_When_GivenValidInput() {
    // Given
    UUID accountId = UUID.randomUUID();

    when(accountRepository.getById(accountId))
        .thenReturn(
            Optional.of(Account.builder()
                .accountId(UUID.randomUUID())
                .name("name")
                .build()));

    when(transactionRepository.findTopByOrderByDateDesc(accountId)).thenReturn(Optional.of(
        Transaction.builder()
            .accountId(accountId)
            .amount(new BigDecimal(450))
            .balance(new BigDecimal(500))
            .build()
    ));

    // When
    withdrawalOperation.operate(accountId, new BigDecimal(300));

    // Then
    verify(accountRepository, times(1)).getById(accountId);
    verify(transactionRepository, times(1)).findTopByOrderByDateDesc(accountId);
    verify(transactionRepository, times(1)).save(any());
  }

}
