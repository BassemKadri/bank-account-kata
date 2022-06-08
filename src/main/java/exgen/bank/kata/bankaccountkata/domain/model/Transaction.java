package exgen.bank.kata.bankaccountkata.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Value
@Builder
public class Transaction {
  UUID transactionId;
  Date date;
  BigDecimal amount;
  TransactionType type;
  BigDecimal balance;
  UUID accountId;
}
