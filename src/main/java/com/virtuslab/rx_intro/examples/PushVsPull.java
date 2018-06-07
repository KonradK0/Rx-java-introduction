package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

class PushVsPull {

    private final static Logger LOG = LoggerFactory.getLogger(PushVsPull.class);

    public static void main(String[] args) {
        IntStream.range(1, 100) //pull 100 items
                .skip(10)
                .limit(5) // choose 5 of skipped and ignore rest
                .mapToObj(integer -> integer + "_StreamTransformation")
                .forEach(LOG::info);

        Observable.range(1, 100) //emits 15 items
                .skip(10)
                .take(5) // push 5 items then unsubscribe
                .map(integer -> integer + "_ObservableTransformation")
                .subscribe(LOG::info);
    }
}
