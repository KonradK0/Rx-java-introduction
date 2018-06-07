package com.virtuslab.rx_intro.task;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.virtuslab.rx_intro.task.ConfirmedTransactionSummarizer.*;
import static com.virtuslab.rx_intro.task.DataGenerator.ALL_CONFIRMED;
import static com.virtuslab.rx_intro.task.DataGenerator.PARTIALLY_CONFIRMED;

public class ConfirmedTransactionSummarizerTest {

    private TestObserver<BigDecimal> testObserver;

    @Before
    public void setUpTestObserver() {
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldSummarizeConfirmedTransactions() {
        final ConfirmedTransactionSummarizer summarizer =
                new ConfirmedTransactionSummarizer(PARTIALLY_CONFIRMED::transactions, PARTIALLY_CONFIRMED::confirmations);

        summarizer
                .summarizeConfirmedTransactions()
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertValue(PARTIALLY_CONFIRMED.expectedValue());
    }

    @Test
    public void shouldSummarizeAllTransactions() {
        final ConfirmedTransactionSummarizer summarizer =
                new ConfirmedTransactionSummarizer(ALL_CONFIRMED::transactions, ALL_CONFIRMED::confirmations);

        summarizer
                .summarizeConfirmedTransactions()
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertValue(ALL_CONFIRMED.expectedValue());
    }

    @Test
    public void shouldWrapErrorIntoException() {
        final ConfirmedTransactionSummarizer summarizer =
                new ConfirmedTransactionSummarizer(ALL_CONFIRMED::transactions, () -> Observable.error(new RuntimeException("Booom")));


        summarizer
                .summarizeConfirmedTransactions()
                .subscribe(testObserver);

        testObserver.assertError(SummarizationException.class);
        testObserver.assertErrorMessage("Booom");
    }
}