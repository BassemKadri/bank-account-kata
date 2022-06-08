package exgen.bank.kata.bankaccountkata.domain.exceptions;

import lombok.Builder;

public class BalanceLowException extends RuntimeException {

  @Builder
  public BalanceLowException(final String message, Throwable cause) {
    super(message, cause);
  }
}
