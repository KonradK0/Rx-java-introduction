package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FirstObservable {

    private final static Logger LOG = LoggerFactory.getLogger(FirstObservable.class);

    public static void main(String[] args) {
        Observable.just("item1")
                .subscribe(
                    item -> LOG.info("OnNext: " + item),
                    error -> LOG.error("OnError", error),
                    () -> LOG.info("OnCompleted")
                );
    }
}
