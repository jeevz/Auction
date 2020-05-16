package com.example.auction.pipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.function.Consumer;

public class Pipe<T, U> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pipe.class);

    private PipeSink sink;
    private Flux<U> flux;
    private ConnectableFlux<T> connectableFlux;

    public Pipe() {

    }

    public static <T, U> Pipe<T, U> create(Handler<T, U> handler) {
//        LOGGER.debug("Creating Pipe");
        Pipe<T, U> pipe = new Pipe();
        pipe.sink = new PipeSink();
        pipe.flux = Flux.create(pipe.sink).handle(handler);
        return pipe;
    }

    public static <T, U> Pipe<T, U> createEmitterPipe() {
        Pipe<T, U> pipe = new Pipe();
        EmitterProcessor<U> processor = EmitterProcessor.create(false);
        pipe.sink = new PipeSink();
        pipe.sink.accept(processor.sink());
        pipe.flux = processor;
        return pipe;
    }

    public <V> Pipe<U, V> add(Handler<U, V> handler) {
        Pipe<U, V> pipe = new Pipe();
        pipe.connectableFlux = flux.publish();
        pipe.flux = pipe.connectableFlux.handle(handler);
        pipe.sink = sink;
        return pipe;
    }

    public Optional<PipeSink> getSink() {
        return Optional.ofNullable(sink);
    }

    public Disposable subscribe() {
        return flux.subscribe();
    }

    public Disposable subscribe(Consumer<U> consumer) {
        return flux.subscribe(consumer);
    }

    public Disposable subscribe(Consumer<U> consumer, Consumer<? super Throwable> error) {
        return flux.subscribe(consumer, error);
    }

    public void subscribe(BaseSubscriber<U> subscriber) {
        flux.subscribe(subscriber);
    }
}
