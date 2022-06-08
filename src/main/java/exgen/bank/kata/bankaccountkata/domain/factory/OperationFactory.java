package exgen.bank.kata.bankaccountkata.domain.factory;

import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;

/**
 * A factory pattern used to distinguish between the different operations
 **/
public class OperationFactory {

  final DepositOperation depositOperation;
  final EmptyOperation emptyOperation;
  final WithdrawalOperation withdrawalOperation;

  public OperationFactory(final DepositOperation depositOperation,
      final EmptyOperation emptyOperation, final WithdrawalOperation withdrawalOperation) {
    this.depositOperation = depositOperation;
    this.emptyOperation = emptyOperation;
    this.withdrawalOperation = withdrawalOperation;
  }

  public Operation getOperation(TransactionType transactionType) {
    switch (transactionType) {
      case DEPOSIT:
        return depositOperation;
      case WITHDRAWL:
        return withdrawalOperation;
      default:
        return emptyOperation;
    }
  }
}
