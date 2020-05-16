package com.example.auction.pipe;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class PipeSink<T> implements Consumer<FluxSink<T>> {

    private FluxSink<T> fluxSink;

    public PipeSink() {

    }

    public void accept(FluxSink<T> fluxSink) {
        this.fluxSink = fluxSink;
    }

    public void publish(T t) {
        fluxSink.next(t);
    }

    public void publish(Throwable t) {
        fluxSink.error(t);
    }
}
