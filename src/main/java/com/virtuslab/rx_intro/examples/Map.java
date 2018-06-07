package com.virtuslab.rx_intro.examples;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Map {

    private final static Logger LOG = LoggerFactory.getLogger(Map.class);

    public static void main(String[] args) {
        Observable.range(1, 10)
                .map(integer -> String.format("Negation of [%s] is [%s]", integer, -integer))
                .subscribe(
                        LOG::info
                );
    }
}
