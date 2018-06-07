package com.virtuslab.rx_intro.examples;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LazyVsEager {

    private final static Logger LOG = LoggerFactory.getLogger(LazyVsEager.class);

    public static void main(String[] args) {
        final Single<String> singleItem =
                Single.just("PRETTY_ITEM")
                        .doOnSuccess(item -> LOG.info("Emitted item {}", item));

        LOG.info("Where is my item?!");

        singleItem.subscribe(item -> LOG.info("Here is my item [{}]", item));
        singleItem.subscribe(item -> LOG.info("Here is my item [{}] once again", item));
    }
}
