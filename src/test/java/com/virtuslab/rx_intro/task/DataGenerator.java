package com.virtuslab.rx_intro.task;


import io.reactivex.Observable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum DataGenerator {

    ALL_CONFIRMED {
        Set<String> ids = Stream.of("1", "2", "3", "4", "5").collect(Collectors.toSet());

        @Override
        Observable<Transaction> transactions() {
            return transactionsOf(ids, BigDecimal.TEN);
        }

        @Override
        Observable<Confirmation> confirmations() {
            return confirmationsOf(ids, true);
        }

        @Override
        BigDecimal expectedValue() {
            return BigDecimal.valueOf(50);
        }
    },

    PARTIALLY_CONFIRMED {
        Set<String> confirmed = Stream.of("1", "2", "3").collect(Collectors.toSet());
        Set<String> notConfirmed = Stream.of("4", "5", "6").collect(Collectors.toSet());

        @Override
        Observable<Transaction> transactions() {
            return transactionsOf(confirmed, BigDecimal.ONE)
                    .concatWith(transactionsOf(notConfirmed, BigDecimal.TEN));
        }

        @Override
        Observable<Confirmation> confirmations() {
            return confirmationsOf(confirmed, true)
                    .concatWith(confirmationsOf(notConfirmed, false));
        }

        @Override
        BigDecimal expectedValue() {
            return BigDecimal.valueOf(3);
        }
    };

    abstract Observable<Transaction> transactions();

    abstract Observable confirmations();

    abstract BigDecimal expectedValue();

    private static Observable<Transaction> transactionsOf(Set<String> ids, BigDecimal value) {
        return Observable.fromIterable(ids)
                .map(id -> transaction(id, value));
    }

    private static Transaction transaction(String id, BigDecimal value) {
        return new Transaction(id, value);
    }

    private static Observable<Confirmation> confirmationsOf(Set<String> transactionIds, boolean isConfirmed) {
        return Observable.fromIterable(transactionIds)
                .map(id -> confirmation(id, isConfirmed));
    }

    private static Confirmation confirmation(String transactionId, boolean isConfirmed) {
        return new Confirmation(transactionId, isConfirmed);
    }

}
