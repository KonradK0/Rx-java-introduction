package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ErrorHandling {

    private final static Logger LOG = LoggerFactory.getLogger(ErrorHandling.class);

    public static void main(String[] args) {
        final Observable<String> brokenStream = Observable.error(new RuntimeException("Boom"));
        brokenStream
                .doOnError(error -> LOG.error("Got error", error))
                .onErrorResumeNext(Observable.just("default1", "default2"))
                .doOnError(error -> LOG.error("Should never happen"))
                .subscribe(
                        LOG::info,
                        error -> LOG.error("Should never happen")
                );
    }

}
