package exgen.bank.kata.bankaccountkata.domain.model;

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
public class Account {
  UUID accountId;
  String name;
}
