package exgen.bank.kata.bankaccountkata.domain.factory;

import exgen.bank.kata.bankaccountkata.domain.model.TransactionType;

/**
 * A factory pattern used to distinguish between the different operations
 **/
public class OperationFactory {

  final DepositOperation depositOperation;
  final EmptyOperation emptyOperation;

  public OperationFactory(DepositOperation depositOperation, EmptyOperation emptyOperation) {
    this.depositOperation = depositOperation;
    this.emptyOperation = emptyOperation;
  }

  public Operation getOperation(TransactionType transactionType) {
    switch (transactionType) {
      case DEPOSIT:
        return depositOperation;
      default:
        return emptyOperation;
    }
  }
}
