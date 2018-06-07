package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SyncVsAsync {

    private final static Logger LOG = LoggerFactory.getLogger(SyncVsAsync.class);

    public static void main(String[] args) {
        Observable.range(1, 10)
                .flatMap(integer ->
                        Observable.range(1, 10)
                                .map(innerInteger -> integer + "+" + innerInteger))
                .subscribe(concat -> LOG.info("Sync: {}", concat));


        Observable.range(1, 10)
                .flatMap(integer ->
                        Observable.range(1, 10)
                                .subscribeOn(Schedulers.io()) //runs below execution on different thread pool
                                .map(innerInteger -> integer + "+" + innerInteger))
                .subscribe(concat -> LOG.info("Async: {}", concat));
    }
}
