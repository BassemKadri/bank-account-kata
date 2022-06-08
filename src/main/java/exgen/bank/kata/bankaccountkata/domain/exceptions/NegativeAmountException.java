package exgen.bank.kata.bankaccountkata.domain.exceptions;

import lombok.Builder;

public class NegativeAmountException extends RuntimeException {

  @Builder
  public NegativeAmountException(final String message, Throwable cause) {
    super(message, cause);
  }
}
