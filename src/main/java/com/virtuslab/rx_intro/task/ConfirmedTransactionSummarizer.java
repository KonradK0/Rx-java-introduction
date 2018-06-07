package com.virtuslab.rx_intro.task;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ConfirmedTransactionSummarizer is responsible for calculation of total confirmed transactions value.
 * HINT:
 * - Use zip operator to match transactions with confirmations. They will appear in order
 * - Filter only confirmed
 * - Aggregate value of confirmed transactions using reduce operator
 *
 * HINT2:
 * - add error handling which will wrap an error into SummarizationException
 *
 */
class ConfirmedTransactionSummarizer {

    private final Supplier<Observable<Transaction>> transactions;
    private final Supplier<Observable<Confirmation>> confirmations;

    ConfirmedTransactionSummarizer(Supplier<Observable<Transaction>> transactions,
                                   Supplier<Observable<Confirmation>> confirmations) {
        this.transactions = transactions;
        this.confirmations = confirmations;
    }

    public Transaction findTransactionOfId(String id){
        return transactions.get().filter(t -> t.transactionId.equals(id)).blockingSingle();
    }

    private Observable<Confirmation> getSummarizationException(Exception e){
        return Observable.error(new SummarizationException(e.getMessage()));
    }

    Single<BigDecimal> summarizeConfirmedTransactions() {
        Observable<Confirmation> zippedTransConf = Observable.zip(transactions.get(), confirmations.get(), (transaction, confirmation) -> new Confirmation(transaction.transactionId, confirmation.isConfirmed));
        return zippedTransConf
                .onErrorResumeNext(Observable.error(new SummarizationException("Booom")))
                .filter(c -> c.isConfirmed)
                .reduce(BigDecimal.ZERO, (sum, confirmation) -> sum.add(findTransactionOfId(confirmation.transactionId).value));
//        return Single.just(sum);
    }

    static class SummarizationException extends RuntimeException {

        public SummarizationException(String message) {
            super(message);
        }
    }
}
