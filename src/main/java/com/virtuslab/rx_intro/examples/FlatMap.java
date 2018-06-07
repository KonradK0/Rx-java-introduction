package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FlatMap {

    private final static Logger LOG = LoggerFactory.getLogger(FlatMap.class);

    public static void main(String[] args) {
        Observable.range(1, 10)
                .flatMapSingle(integer ->
                        Single.just(10)
                                .map(ten -> ten + integer))
                .subscribe(
                        addition -> LOG.info("Addition of integers from range and 10 is equal to {}", addition)
                );
    }
}
