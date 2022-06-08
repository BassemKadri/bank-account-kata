package exgen.bank.kata.bankaccountkata.domain.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import exgen.bank.kata.bankaccountkata.domain.exceptions.AccountNotFoundException;
import exgen.bank.kata.bankaccountkata.domain.exceptions.NegativeAmountException;
import exgen.bank.kata.bankaccountkata.domain.model.Account;
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
public class DepositOperationTest {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private AccountRepository accountRepository;

  private DepositOperation depositOperation;

  @BeforeEach
  void setup() {
    this.depositOperation = new DepositOperation(transactionRepository, accountRepository);
  }

  @Test
  public void Should_GetNegativeAmountException_When_DepositNegativeAmount() {
    // Given
    UUID accountId = UUID.randomUUID();

    // When
    assertThrows(NegativeAmountException.class,
        () -> depositOperation.operate(accountId, new BigDecimal(-6)));

    // Then
    verifyNoMoreInteractions(accountRepository);
    verifyNoMoreInteractions(transactionRepository);
  }

  @Test
  public void Should_GetAccountNotFoundException_When_DepositWithUnknownAccountId() {
    // Given
    UUID accountId = UUID.randomUUID();

    // When
    assertThrows(AccountNotFoundException.class,
        () -> depositOperation.operate(accountId, new BigDecimal(234)));

    // Then
    verify(accountRepository, times(1)).getById(accountId);
    verifyNoMoreInteractions(transactionRepository);
  }

  @Test
  public void Should_DepositAmount_When_GivenValidInput() {
    // Given
    UUID accountId = UUID.randomUUID();

    when(accountRepository.getById(accountId))
        .thenReturn(
            Optional.of(Account.builder().accountId(UUID.randomUUID()).name("name").build()));

    // When
    depositOperation.operate(accountId, new BigDecimal(500));

    // Then
    verify(accountRepository, times(1)).getById(accountId);
    verify(transactionRepository, times(1)).findTopByOrderByDateDesc(accountId);
    verify(transactionRepository, times(1)).save(any());
  }

}
