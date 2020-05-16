package com.example.auction.pipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface Handler<T, V> extends BiConsumer<T, SynchronousSink<V>> {
    Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    default void accept(T t, SynchronousSink<V> sink) {
        try {
            V val = handle(t);
            if (val != null) {
                sink.next(val);
            } else {
                LOGGER.debug("null values are dropped");
            }
        } catch(Exception e) {
            sink.error(e);
        }
    }

    V handle(T t);
}
