package exgen.bank.kata.bankaccountkata.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import exgen.bank.kata.bankaccountkata.domain.factory.OperationFactory;
import exgen.bank.kata.bankaccountkata.domain.model.Transaction;
import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;
import exgen.bank.kata.bankaccountkata.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @Mock
  private OperationFactory operationFactory;
  @Mock
  private TransactionRepository transactionRepository;

  AccountServiceImpl accountServiceImpl;

  @BeforeEach
  void setup() {
    this.accountServiceImpl = new AccountServiceImpl(operationFactory, transactionRepository);
  }

  @Test
  public void Should_LogHistoryAccount_When_GivenDates() throws ParseException {
    // Given
    List<Transaction> transactions = Arrays.asList(Transaction.builder()
            .date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("27/05/2022 00:00:00"))
            .type(TransactionType.DEPOSIT)
            .amount(new BigDecimal(100))
            .balance(new BigDecimal(500))
            .build(),
        Transaction.builder()
            .date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("30/05/2022 00:00:00"))
            .type(TransactionType.WITHDRAWL)
            .amount(new BigDecimal(50))
            .balance(new BigDecimal(600))
            .build());

    String historyLog =
        "TransactionType,Date                               ,Amount    ,Balance             \n"
            + "DEPOSIT         Fri May 27 00:00:00 CEST 2022        100       500\n"
            + "WITHDRAWL       Mon May 30 00:00:00 CEST 2022         50       600\n";

    // When
    String loggedTransactions = accountServiceImpl.logHistoryAccount(transactions);

    // Then
    assertEquals(loggedTransactions, historyLog);
  }
}
