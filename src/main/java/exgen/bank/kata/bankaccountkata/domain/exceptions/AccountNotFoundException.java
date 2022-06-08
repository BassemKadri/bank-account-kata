package exgen.bank.kata.bankaccountkata.domain.exceptions;

import lombok.Builder;

public class AccountNotFoundException extends RuntimeException {

  @Builder
  public AccountNotFoundException(final String message, Throwable cause) {
    super(message, cause);
  }
}