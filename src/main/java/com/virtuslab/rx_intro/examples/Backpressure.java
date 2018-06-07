package com.virtuslab.rx_intro.examples;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Backpressure {

    private final static Logger LOG = LoggerFactory.getLogger(Backpressure.class);

    private static class ObservableBackpresure {

        public static void main(String[] args) throws InterruptedException {
            Observable.range(1, 512)
                    .map(id -> "ObservableString_" + id)
                    .doOnNext(string -> LOG.info("Got {}", string))
                    .observeOn(Schedulers.io())
                    .subscribe(
                            string -> {
                                LOG.info("Consumed {}", string);
                                Thread.sleep(50);
                            }
                    );

            Thread.sleep(10000);
        }

    }

    private static class FlowableBackpressure {

        public static void main(String[] args) throws InterruptedException {
            Flowable.range(1, 512)
                    .map(id -> "FlowableString_" + id)
                    .doOnNext(string -> LOG.info("Got {}", string))
                    .observeOn(Schedulers.io())
                    .subscribe(
                            string -> {
                                LOG.info("Consumed {}", string);
                                Thread.sleep(50);
                            }
                    );

            Thread.sleep(10000);
        }

    }
}